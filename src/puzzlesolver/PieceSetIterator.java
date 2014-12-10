package puzzlesolver;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PieceSetIterator implements Iterator<PuzzlePiece> {
	private PuzzlePiece row;
	private PuzzlePiece col;
	public PieceSetIterator(PieceSet ps) {
		row = ps.getNWCorner();
		col = ps.getNWCorner();
	}	
	public boolean hasNext() {
		if (col.getEast() == null && row.getSouth() == null) {
			return false;
		}
		return true;
	}
	public PuzzlePiece next() {
		if (col.getEast() != null) {
			return col.getEast();
		} else if (row.getSouth() != null) {
			return row.getSouth();
		} else {
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}	
}
