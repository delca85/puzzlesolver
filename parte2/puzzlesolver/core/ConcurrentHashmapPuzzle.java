package puzzlesolver.core;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Concurrent, thread-safe HashmapPuzzle.
 * Handles each row in a parallel fashion.
 */
public class ConcurrentHashmapPuzzle extends HashmapPuzzle {
	
	private final ExecutorService executor;
	
	/**
	 * @param executor An ExecutorService instance, will be used to concurrently solve the puzzle
	 */
	public ConcurrentHashmapPuzzle(ExecutorService executor) {
		this.executor = executor;
	}
	
	/**
	 * Links together the pieces forming a row.
	 * This is thread-safe if it can be guaranteed that no other
	 * Linker instances are working on the same row.
	 */
	class RowLinker implements Callable<Void> {
		
		IPuzzlePiece t; 
		HashMap<String, IPuzzlePiece> map;
		
		RowLinker(IPuzzlePiece t, HashMap<String, IPuzzlePiece> map) {
			this.t = t;
			this.map = map;
		}
		
		@Override
		public Void call() throws MissingPiecesException {
			while (!t.isECol()) {
				if (!t.isSRow()) {
					IPuzzlePiece southNeighbour = map.get(t.getSouthId());
					// This is  ~O(1) for realistic inputs
					// HashMap.get is thread-safe
					if (southNeighbour == null) {
						throw new MissingPiecesException();
						// The piece set is incomplete (or southId is wrong)
					}
					t.setSouth(southNeighbour);
					// southNeighbour.setNorth(t);
				}

				if (!t.isWCol()) {
					IPuzzlePiece westNeighbour = map.get(t.getWestId());
					if (westNeighbour == null) {
						throw new MissingPiecesException();
					}
					t.setWest(westNeighbour);
				}
				
				if (!t.isNRow()) {
					IPuzzlePiece northNeighbour = map.get(t.getNorthId());
					if (northNeighbour == null) {
						throw new MissingPiecesException();
					}
					t.setNorth(northNeighbour);
				}

				IPuzzlePiece eastNeighbour = map.get(t.getEastId());
				if (eastNeighbour == null) {
					throw new MissingPiecesException();
				}
				t.setEast(eastNeighbour);

				t = t.getEast();

			}

			if (!t.isSRow()) {
				IPuzzlePiece southNeighbour = map.get(t.getSouthId());
				if (southNeighbour == null) {
					throw new MissingPiecesException();
				}
				t.setSouth(southNeighbour);
			}

			if (!t.isWCol()) {
				IPuzzlePiece westNeighbour = map.get(t.getWestId());
				if (westNeighbour == null) {
					throw new MissingPiecesException();
				}
				t.setWest(westNeighbour);
			}
			
			if (!t.isNRow()) {
				IPuzzlePiece northNeighbour = map.get(t.getNorthId());
				if (northNeighbour == null) {
					throw new MissingPiecesException();
				}
				t.setNorth(northNeighbour);
			}

			return null;
		}		
	}

	private void linkColSE () throws MissingPiecesException {
		assert(NWCorner != null);
		
		IPuzzlePiece it = NWCorner;
		int rows = 0;
		// JCIP
		CompletionService<Void> completionService = new ExecutorCompletionService<Void>(executor);
		while (!it.isSRow()) {
			completionService.submit(new RowLinker(it, pieceHashMap));
			it = pieceHashMap.get(it.getSouthId());
			if (it == null) {
				throw new MissingPiecesException();
			}
			rows++;
		}
		completionService.submit(new RowLinker(it, pieceHashMap));
		rows++;

		try {
			for (int i = 0; i < rows; i++) {
				Future<Void> f = completionService.take();
				f.get();
			}
			// This completes without blocking or raising
			// execptions iff exactly n Callables have completed
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			Throwable t = e.getCause();
			  if (t instanceof RuntimeException) 
		            throw (RuntimeException) t;
		        else if (t instanceof Error)
		            throw (Error) t;
		        else if (t instanceof MissingPiecesException)
		        	throw (MissingPiecesException) t;
		        else
		            throw new IllegalStateException("Not unchecked", e);
		}
	}
	
	public void solve() throws MissingPiecesException {
		assert(NWCorner != null);
		if (solved == false) {
			linkColSE();
		}
		solved = true;
	}

}
