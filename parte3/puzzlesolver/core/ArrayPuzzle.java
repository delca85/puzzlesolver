package puzzlesolver.core;

import java.io.Serializable;
import java.util.Iterator;

public abstract class ArrayPuzzle implements IPuzzle, Serializable {

	private class ArrayPuzzleIterator implements Iterator<Iterator<IPuzzlePiece>> {
		
		private class ArrayPuzzleRowIterator implements Iterator<IPuzzlePiece> {
			IPuzzlePiece[] array;
			int j;
			ArrayPuzzleRowIterator(IPuzzlePiece[] array) {
				this.array = array;
				j = 0;
			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean hasNext() {
				if (j < array.length - 1) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public IPuzzlePiece next() {
				return array[j++];
			}
		}

		int i;
		IPuzzlePiece[][] array;
		
		ArrayPuzzleIterator (IPuzzlePiece[][] array) {
			this.array = array;
			i = 0;
		}
		
		@Override
		public boolean hasNext() {
			if (i < array.length - 1) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Iterator<IPuzzlePiece> next() {
			return new ArrayPuzzleRowIterator(array[i++]);
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	
	
	
	IPuzzlePiece[][] array;
	int cols;
	int rows;
	protected boolean solved;
	
	public ArrayPuzzle(int rows, int cols) {
		array = new IPuzzlePiece[rows][cols];
	} 
	
	public void put(IPuzzlePiece piece, int row, int col) {
		array[row][col] = piece;
	}

	@Override
	public Iterator<Iterator<IPuzzlePiece>> iterator() {
		if (!solved) {
			throw new PuzzleNotSolvedException();
		}
		return new ArrayPuzzleIterator(array);
	}

	@Override
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

	@Override
	public int getRows() throws PuzzleNotSolvedException {
		return rows;
	}

	@Override
	public int getCols() throws PuzzleNotSolvedException {
		return cols;
	}

}
