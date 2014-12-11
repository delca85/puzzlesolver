package puzzlesolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BasicPieceSet implements PieceSet {
	
	PuzzlePiece NWCorner = null;
	HashMap<String,PuzzlePiece> pieceHashMap = new HashMap<String,PuzzlePiece>();
	
	static class NWRecursiveSolver {
		
		private static boolean isFullyLinked(PuzzlePiece p) {
			if (p == null) {
				throw new IllegalArgumentException();
			}
			if ((!p.isSRow() && p.getSouth() == null) ||
				(!p.isNRow() && p.getNorth() == null) ||
				(!p.isWRow() && p.getWest() == null) ||
				(!p.isERow() && p.getEast() == null)) {
				return false;
			}
			return true;
		}

		private static void BFSSolve (PuzzlePiece root, HashMap<String, PuzzlePiece> map) throws MissingPiecesException {
			Queue<PuzzlePiece> queued = new LinkedList<PuzzlePiece>();
			Set<PuzzlePiece> visited = new HashSet<PuzzlePiece>();
			visited.add(root);
			queued.add(root);
			while (!queued.isEmpty()) {

				PuzzlePiece t = queued.remove();
				
				if (t == null) {
					return;
				}

				PuzzlePiece n = t.getNorth();
				PuzzlePiece s = t.getSouth();
				PuzzlePiece w = t.getWest();
				PuzzlePiece e = t.getEast();
					
				if (!visited.contains(n)) {
					if (!t.isNRow() && n == null) { 
						PuzzlePiece northNeighbour = map.get(t.getNorthId());
						if (northNeighbour == null) {
							throw new MissingPiecesException();
						}
						t.setNorth(northNeighbour);
					}
					visited.add(n);
					queued.add(n);
				}

				if (!visited.contains(s)) {
					if (!t.isSRow() && s == null) { 
						PuzzlePiece southNeighbour = map.get(t.getSouthId());
						if (southNeighbour == null) {
							throw new MissingPiecesException();
						}
						t.setSouth(southNeighbour);
					}
					visited.add(s);
					queued.add(s);
				}

				if (!visited.contains(w)) {
					if (!t.isWRow() && w == null) { 
						PuzzlePiece westNeighbour = map.get(t.getWestId());
						if (westNeighbour == null) {
							throw new MissingPiecesException();
						}						
						t.setWest(westNeighbour);
					}
					visited.add(w);
					queued.add(w);
				}

				if (!visited.contains(e)) {
					if (!t.isERow() && e == null) { 
						PuzzlePiece eastNeighbour = map.get(t.getEastId());
						if (eastNeighbour == null) {
							throw new MissingPiecesException();
						}						
						t.setEast(eastNeighbour);
					}
					visited.add(e);
					queued.add(e);
				}

				
			}
			
		}
		
		private static void NWrecursiveSolve(PuzzlePiece p, HashMap<String, PuzzlePiece> map) throws MissingPiecesException {
			
			if (p.getSouth() == null && !p.isSRow()) {
				// Still not linked south and needs linking
				PuzzlePiece southNeighbour;
				southNeighbour = map.get(p.getSouthId());
				if (southNeighbour == null) {
					// Piece by this ID was not found.
					// Method got called /before/ loading all the pieces.
					// (Depends on HashMap behaviour)
					throw new MissingPiecesException();
				}
				p.setSouth(southNeighbour);
				southNeighbour.setNorth(p);
				// Link south neighbour with piece in both directions
				if (!isFullyLinked(p.getSouth())) {
					/*
					 * This is not exactly necessary but 
					 * avoids visiting a piece only to return
					 * after finding out there was nothing to do
					 */
					NWrecursiveSolve(p.getSouth(), map);
				}
			}
			
			if (p.getEast() == null && !p.isERow()) {
				PuzzlePiece eastNeighbour;
				try { 
					eastNeighbour = map.get(p.getEastId());
				} catch (NullPointerException e) { 
					throw new MissingPiecesException();
				}
				if (eastNeighbour == null) {
					throw new MissingPiecesException();
				}			
				p.setEast(eastNeighbour);
				eastNeighbour.setWest(p);
				if (!isFullyLinked(p.getEast())) {
					NWrecursiveSolve(p.getEast(), map);
				}
			}
		};
	}
	
	public void addPiece(PuzzlePiece p) {
		if (p.isNWCorner()) {
			NWCorner = p;
		}
		pieceHashMap.put(p.getId(), p);
	}

	public void solve() throws MissingPiecesException {
		NWRecursiveSolver.BFSSolve(NWCorner, pieceHashMap);
	}

	public PieceSetIterator iterator() {
		return new PieceSetIterator(this);
	}

	public PuzzlePiece getNWCorner() {
		return NWCorner;
	}

}
