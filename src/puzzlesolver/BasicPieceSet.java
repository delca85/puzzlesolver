package puzzlesolver;

import java.util.HashMap;

public class BasicPieceSet implements PieceSet {
	
	PuzzlePiece NWCorner = null;
	HashMap<String,PuzzlePiece> map = new HashMap<String,PuzzlePiece>();
	
	private static void NWrecursiveSolve(PuzzlePiece p, HashMap<String, PuzzlePiece> map) throws MissingPiecesException {
		System.out.println(p.getId());
		if (p.getWest() == null && !p.isWRow()) {
			PuzzlePiece westNeighbour;
			try { 
				westNeighbour = map.get(p.getWestId());
			} catch (NullPointerException e) { // Piece by this ID was not found.
				throw new MissingPiecesException();
			}
			if (westNeighbour == null) {
				throw new MissingPiecesException();
			}
			p.setWest(westNeighbour);
			westNeighbour.setEast(p);
			NWrecursiveSolve(p.getWest(), map);
		}
		if (p.getSouth() == null && !p.isSRow()) {
			PuzzlePiece southNeighbour;
			try { 
				southNeighbour = map.get(p.getSouthId());
			} catch (NullPointerException e) { // Piece by this ID was not found.
				throw new MissingPiecesException();
			}
			if (southNeighbour == null) {
				throw new MissingPiecesException();
			}
			p.setSouth(southNeighbour);
			southNeighbour.setNorth(p);
			NWrecursiveSolve(p.getSouth(), map);
		}
		if (p.getEast() == null && !p.isERow()) {
			PuzzlePiece eastNeighbour;
			try { 
				eastNeighbour = map.get(p.getEastId());
			} catch (NullPointerException e) { // Piece by this ID was not found.
				throw new MissingPiecesException();
			}
			if (eastNeighbour == null) {
				throw new MissingPiecesException();
			}			
			p.setEast(eastNeighbour);
			eastNeighbour.setWest(p);
			NWrecursiveSolve(p.getEast(), map);
		}
	};
	
	public void addPiece(PuzzlePiece p) {
		if (p.isNWCorner()) {
			NWCorner = p;
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
