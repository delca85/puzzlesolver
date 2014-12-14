package puzzlesolver.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractBasicPuzzle implements Puzzle {
	private class BasicPuzzleIterator implements Iterator<Iterator<PuzzlePiece>> {
		private class BasicPuzzleRowIterator implements Iterator<PuzzlePiece> {
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

		public BasicPuzzleIterator(AbstractBasicPuzzle abstractBasicPuzzle) throws PuzzleNotSolvedException {
			if (!solved) {
				throw new PuzzleNotSolvedException();
			}
			row = abstractBasicPuzzle.getNWCorner();
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

	protected PuzzlePiece NWCorner = null;
	protected HashMap<String, PuzzlePiece> pieceHashMap = new HashMap<String, PuzzlePiece>();
	protected boolean solved = false;

	

	public void addPiece(PuzzlePiece p) {
		solved = false;
		if (p.isNWCorner()) {
			NWCorner = p;
		}
		pieceHashMap.put(p.getId(), p);
	}

	abstract public void solve() throws MissingPiecesException;
	
	public Iterator<Iterator<PuzzlePiece>> iterator() throws PuzzleNotSolvedException {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}
		return new BasicPuzzleIterator(this);
	}

	private PuzzlePiece getNWCorner() {
		return NWCorner;
	}

	public int getRows() throws PuzzleNotSolvedException {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}
		int count = 0;
		for (Iterator<Iterator<PuzzlePiece>> rowIt = iterator();
		        rowIt.hasNext(); rowIt.next()) {
			count++;
		}
		return count;
	}

	public int getCols() throws PuzzleNotSolvedException {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}
		Iterator<Iterator<PuzzlePiece>> rowIt = iterator();
		if (!rowIt.hasNext()) {
			return 0;
		}
		int count = 0;
		for (Iterator<PuzzlePiece> colIt = rowIt.next();
		        colIt.hasNext(); colIt.next()) {
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
		        rowIt.hasNext();) {
			for (Iterator<PuzzlePiece> colIt = rowIt.next();
			        colIt.hasNext();) {
				output += colIt.next().getCharacter();
			}
		}
		return output;
	}

	public String toString() {
		try {
			return "A " + getCols() + "x" + getRows() + " puzzle";
		} catch (PuzzleNotSolvedException e) {
			return "An unsolved puzzle";
		}
	}
}
