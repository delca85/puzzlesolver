package puzzlesolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

public class BasicPuzzle implements Puzzle {
	private class BasicPuzzleIterator implements Iterator<Iterator<PuzzlePiece>> {
		class BasicPuzzleRowIterator implements Iterator<PuzzlePiece>{
			private PuzzlePiece col;
			
			public BasicPuzzleRowIterator(PuzzlePiece p) {
				col = p;
			}
			
			public boolean hasNext() {
				if (col == null) {
					return false;
				}
				return true;
			}
			
			public PuzzlePiece next() {
				if (col != null) {
					PuzzlePiece res = col;
					col = col.getEast();
					return res;
				} else {
					throw new NoSuchElementException();
				}

			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}
		
		private PuzzlePiece row;
		
		public BasicPuzzleIterator(BasicPuzzle ps) throws PuzzleNotSolvedException {
			if (!solved) {
				throw new PuzzleNotSolvedException();
			}
			row = ps.getNWCorner();
		}	
		
		public boolean hasNext() {
			if (row == null) {
				return false;
			}
			return true;
		}
		
		public Iterator<PuzzlePiece> next() {
			if (row != null) {
				PuzzlePiece res = row;
				row = row.getSouth();
				return new BasicPuzzleRowIterator(res);
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}	
	}
	
	private PuzzlePiece NWCorner = null;
	private HashMap<String,PuzzlePiece> pieceHashMap = new HashMap<String,PuzzlePiece>();
	private boolean solved = false;
	
	private static void BFSSolve (PuzzlePiece root, HashMap<String, PuzzlePiece> map) throws MissingPiecesException {
		// Implements a textbook breadth first traversal with minimal adjustments.
		Queue<PuzzlePiece> queued = new LinkedList<PuzzlePiece>();
		Set<String> visited = new HashSet<String>();

		visited.add(root.getId());
		queued.add(root);
		
		while (!queued.isEmpty()) {
			PuzzlePiece t = queued.remove();

			String n = t.getNorthId();
			String s = t.getSouthId();
			String w = t.getWestId();
			String e = t.getEastId();
				
			if (!visited.contains(n)) {
				if (!t.isNRow()) { 
					/*
					 *  We don't care about the northern neighbour if this is part of the topmost row
					 */ 
					PuzzlePiece northNeighbour = map.get(t.getNorthId());
					// This is  ~O(1) for realistic inputs
					if (northNeighbour == null) {
						throw new MissingPiecesException();
						// The piece set is incomplete (or northId is wrong)
					}
					t.setNorth(northNeighbour);
					visited.add(n);
					// Will pay visit in due time
					queued.add(northNeighbour);
				}
			}

			if (!visited.contains(s)) {
				if (!t.isSRow()) { 
					PuzzlePiece southNeighbour = map.get(t.getSouthId());
					if (southNeighbour == null) {
						throw new MissingPiecesException();
					}
					t.setSouth(southNeighbour);
					visited.add(s);
					queued.add(southNeighbour);
				}
			}

			if (!visited.contains(w)) {
				if (!t.isWCol()) { 
					PuzzlePiece westNeighbour = map.get(t.getWestId());
					if (westNeighbour == null) {
						throw new MissingPiecesException();
					}						
					t.setWest(westNeighbour);
					visited.add(w);
					queued.add(westNeighbour);
				}
			}

			if (!visited.contains(e)) {
				if (!t.isECol()) { 
					PuzzlePiece eastNeighbour = map.get(t.getEastId());
					if (eastNeighbour == null) {
						throw new MissingPiecesException();
					}						
					t.setEast(eastNeighbour);
					visited.add(e);
					queued.add(eastNeighbour);
				}
			}			
		}
	}

	
	public void addPiece(PuzzlePiece p) {
		solved = false;
		
		if (p.isNWCorner()) {
			NWCorner = p;
		}
		pieceHashMap.put(p.getId(), p);
	}

	public void solve() throws MissingPiecesException {
		if (solved == false) {
			BFSSolve(NWCorner, pieceHashMap);
		}
		solved = true;
	}

	public Iterator<Iterator<PuzzlePiece>> iterator() throws PuzzleNotSolvedException {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}
		return new BasicPuzzleIterator(this);
	}

	public PuzzlePiece getNWCorner() {
		return NWCorner;
	}

	public int getRows() throws PuzzleNotSolvedException {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}		
		int count = 0;
		for (Iterator<Iterator<PuzzlePiece>> rowIt = iterator(); 
				rowIt.hasNext(); rowIt.next()){
				count++;
		}
		return count;
	}

	public int getCols() throws PuzzleNotSolvedException {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}				
		Iterator<Iterator<PuzzlePiece>> rowIt = iterator();
		if (!rowIt.hasNext()) { return 0; }
		int count = 0;
		for (Iterator<PuzzlePiece> colIt = rowIt.next(); 
				colIt.hasNext(); colIt.next()){
				count++;
		}
		return count;
	}
	
	public String getSolution() throws PuzzleNotSolvedException {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}
		String output = "";
		for (Iterator<Iterator<PuzzlePiece>> rowIt = iterator(); 
				 rowIt.hasNext();){
			for (Iterator<PuzzlePiece> colIt = rowIt.next(); 
				 colIt.hasNext();){
				output += colIt.next().getCharacter();
			}
		}			 
		return output;
	}
	
	public String toString() {
		try {
			return "A "+getCols()+"x"+getRows()+" puzzle";
		} catch (PuzzleNotSolvedException e) {
			return "An unsolved puzzle";
		}
	}
}
