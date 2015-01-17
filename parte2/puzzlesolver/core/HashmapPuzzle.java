package puzzlesolver.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Abstract class; provides a skeleton HashMap-based storage IPuzzlePiece and a bunch of helper methods.
 * To be instantiated solve() needs to be implemented with a chosen algorithm,
 */
public abstract class HashmapPuzzle implements IPuzzle {
	private class HashmapPuzzleIterator implements Iterator<Iterator<IPuzzlePiece>> {
		private class HashmapPuzzleRowIterator implements Iterator<IPuzzlePiece> {
			private IPuzzlePiece col;

			public HashmapPuzzleRowIterator(IPuzzlePiece p) {
				col = p;
			}

			public boolean hasNext() {
				if (col == null) {
					return false;
				}
				return true;
			}

			public IPuzzlePiece next() {
				if (col != null) {
					IPuzzlePiece res = col;
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

		private IPuzzlePiece row;

		public HashmapPuzzleIterator(HashmapPuzzle hashmapPuzzle) throws PuzzleNotSolvedException {
			if (!solved) {
				throw new PuzzleNotSolvedException();
			}
			row = hashmapPuzzle.getNWCorner();
		}

		public boolean hasNext() {
			if (row == null) {
				return false;
			}
			return true;
		}

		public Iterator<IPuzzlePiece> next() {
			if (row != null) {
				IPuzzlePiece res = row;
				row = row.getSouth();
				return new HashmapPuzzleRowIterator(res);
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	protected IPuzzlePiece NWCorner = null;
	protected HashMap<String, IPuzzlePiece> pieceHashMap = new HashMap<String, IPuzzlePiece>();
	protected boolean solved = false;
	

	public void addPiece(IPuzzlePiece p) {
		solved = false;
		if (p.isNWCorner()) {
			NWCorner = p;
		}
		pieceHashMap.put(p.getId(), p);
	}

	abstract public void solve() throws MissingPiecesException;
	
	public Iterator<Iterator<IPuzzlePiece>> iterator() throws PuzzleNotSolvedException {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}
		return new HashmapPuzzleIterator(this);
	}

	private IPuzzlePiece getNWCorner() {
		return NWCorner;
	}

	public int getRows() throws PuzzleNotSolvedException {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}
		int count = 0;
		for (Iterator<Iterator<IPuzzlePiece>> rowIt = iterator();
		        rowIt.hasNext(); rowIt.next()) {
			count++;
		}
		return count;
	}

	public int getCols() throws PuzzleNotSolvedException {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}
		Iterator<Iterator<IPuzzlePiece>> rowIt = iterator();
		if (!rowIt.hasNext()) {
			return 0;
		}
		int count = 0;
		for (Iterator<IPuzzlePiece> colIt = rowIt.next();
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
		for (Iterator<Iterator<IPuzzlePiece>> rowIt = iterator();
		        rowIt.hasNext();) {
			for (Iterator<IPuzzlePiece> colIt = rowIt.next();
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
