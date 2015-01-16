package puzzlesolver.core;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ConcurrentHashmapPuzzle extends HashmapPuzzle {
	
	private final ExecutorService executor;
	
	public ConcurrentHashmapPuzzle(ExecutorService executor) {
		this.executor = executor;
	}
	
	class Linker implements Callable<Void> {
		
		IPuzzlePiece t; 
		HashMap<String, IPuzzlePiece> map;
		
		Linker(IPuzzlePiece t, HashMap<String, IPuzzlePiece> map) {
			this.t = t;
			this.map = map;
		}
		
		private Void linkSE() throws MissingPiecesException {
			while (!t.isECol()) {
				if (!t.isSRow()) {
					IPuzzlePiece southNeighbour = map.get(t.getSouthId());
					// This is  ~O(1) for realistic inputs
					if (southNeighbour == null) {
						throw new MissingPiecesException();
						// The piece set is incomplete (or northId is wrong)
					}
					t.setSouth(southNeighbour);
					southNeighbour.setNorth(t);
				}

				IPuzzlePiece eastNeighbour = map.get(t.getEastId());
				// This is  ~O(1) for realistic inputs
				if (eastNeighbour == null) {
					throw new MissingPiecesException();
					// The piece set is incomplete (or northId is wrong)
				}
				t.setEast(eastNeighbour);
				eastNeighbour.setWest(t);
				t = t.getEast();

			}

			if (!t.isSRow()) {
				IPuzzlePiece southNeighbour = map.get(t.getSouthId());
				// This is  ~O(1) for realistic inputs
				if (southNeighbour == null) {
					throw new MissingPiecesException();
					// The piece set is incomplete (or northId is wrong)
				}
				t.setSouth(southNeighbour);
				southNeighbour.setNorth(t);
			}
			
			return null;
		}
		
		@Override
		public Void call() throws Exception {
			return linkSE();
		}
		
	}

	private void linkColSE () throws MissingPiecesException {
		assert(NWCorner != null);
		
		IPuzzlePiece it = NWCorner;
		int rows = 0;
		
		CompletionService<Void> completionService = new ExecutorCompletionService<Void>(executor);
		while (!it.isSRow()) {
			completionService.submit(new Linker(it, pieceHashMap));
			it = pieceHashMap.get(it.getSouthId());
			if (it == null) {
				throw new MissingPiecesException();
			}
			rows++;
		}
		completionService.submit(new Linker(it, pieceHashMap));
		rows++;
		//
		
		try {
			for (int i = 0; i < rows; i++) {
				Future<Void> f = completionService.take();
				f.get();
				// OK
			}
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
