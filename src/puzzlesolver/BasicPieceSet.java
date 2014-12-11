package puzzlesolver;

import java.util.HashMap;

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
		NWRecursiveSolver.NWrecursiveSolve(NWCorner, pieceHashMap);
	}

	public PieceSetIterator iterator() {
		return new PieceSetIterator(this);
	}

	public PuzzlePiece getNWCorner() {
		return NWCorner;
	}

}
