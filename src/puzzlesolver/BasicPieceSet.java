package puzzlesolver;

import java.util.HashMap;

public class BasicPieceSet implements PieceSet {
	
	PuzzlePiece NWCorner = null;
	HashMap<String,PuzzlePiece> map;
	
	private static void NWrecursiveSolve(PuzzlePiece p, HashMap<String, PuzzlePiece> map) throws MissingPiecesException {
		// PRE: A nord e' a posto
		if (p.getNorth() == null && !p.isNRow()) {
			throw new IllegalArgumentException();
		}
		
		if (p.getWest() == null && !p.isWRow()) {
			PuzzlePiece westNeighbour;
			try { 
				westNeighbour = map.get(p.getWestId());
			} catch (NullPointerException e) { // Piece by this ID was not found.
				throw new MissingPiecesException();
			}
			p.setWest(westNeighbour);
			NWrecursiveSolve(p.getWest(), map);
		}
		if (p.getSouth() == null && !p.isSRow()) {
			PuzzlePiece southNeighbour;
			try { 
				southNeighbour = map.get(p.getSouthId());
			} catch (NullPointerException e) { // Piece by this ID was not found.
				throw new MissingPiecesException();
			}
			p.setSouth(southNeighbour);
			NWrecursiveSolve(p.getSouth(), map);
		}
		if (p.getEast() == null && !p.isERow()) {
			PuzzlePiece eastNeighbour;
			try { 
				eastNeighbour = map.get(p.getEastId());
			} catch (NullPointerException e) { // Piece by this ID was not found.
				throw new MissingPiecesException();
			}
			p.setEast(eastNeighbour);
			NWrecursiveSolve(p.getEast(), map);
		}
	};
	
	public void queuePiece(PuzzlePiece p) {
		if (p.isNWCorner()) {
		}
		map.put(p.getId(), p);
	}

	public void solve() throws MissingPiecesException {
		NWrecursiveSolve(NWCorner, map);
	}

	public PieceSetIterator iterator() {
		return new PieceSetIterator(this);
	}

	public PuzzlePiece getNWCorner() {
		return NWCorner;
	}

}
