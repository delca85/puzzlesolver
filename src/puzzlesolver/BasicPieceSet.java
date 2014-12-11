package puzzlesolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BasicPieceSet implements PieceSet {
	
	private PuzzlePiece NWCorner = null;
	private HashMap<String,PuzzlePiece> pieceHashMap = new HashMap<String,PuzzlePiece>();

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
				if (!t.isWRow()) { 
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
				if (!t.isERow()) { 
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
		if (p.isNWCorner()) {
			NWCorner = p;
		}
		pieceHashMap.put(p.getId(), p);
	}

	public void solve() throws MissingPiecesException {
		BFSSolve(NWCorner, pieceHashMap);
	}

	public PieceSetIterator iterator() {
		return new PieceSetIterator(this);
	}

	public PuzzlePiece getNWCorner() {
		return NWCorner;
	}

}
