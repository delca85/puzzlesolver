package puzzlesolver;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PieceSetIterator implements Iterator<Iterator<PuzzlePiece>> {
	class PieceSetRowIterator implements Iterator<PuzzlePiece>{
		private PuzzlePiece col;
		
		public PieceSetRowIterator(PuzzlePiece p) {
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
	
	public PieceSetIterator(PieceSet ps) {
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
			return new PieceSetRowIterator(res);
		} else {
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}	
}
