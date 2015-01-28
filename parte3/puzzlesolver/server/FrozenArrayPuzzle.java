package puzzlesolver.server;

import java.io.Serializable;
import java.util.Iterator;

import puzzlesolver.core.ArrayPuzzle;
import puzzlesolver.core.IPuzzle;
import puzzlesolver.core.IPuzzlePiece;
import puzzlesolver.core.MissingPiecesException;

public class FrozenArrayPuzzle extends ArrayPuzzle implements Serializable {

	private class StringPuzzlePiece implements IPuzzlePiece, Serializable {
		char character;
		
		StringPuzzlePiece(char character) {
			this.character = character;
		}
		
		@Override
		public String getId() {
			throw new RuntimeException();
		}

		@Override
		public char getCharacter() {
			return character;
		}

		@Override
		public IPuzzlePiece getNorth() {
			throw new RuntimeException();
		}

		@Override
		public IPuzzlePiece getSouth() {
			throw new RuntimeException();
		}

		@Override
		public IPuzzlePiece getWest() {
			throw new RuntimeException();
		}

		@Override
		public IPuzzlePiece getEast() {
			throw new RuntimeException();
		}

		@Override
		public void setNorth(IPuzzlePiece p) {
			throw new RuntimeException();
		}

		@Override
		public void setSouth(IPuzzlePiece p) {
			throw new RuntimeException();
		}

		@Override
		public void setWest(IPuzzlePiece p) {
			throw new RuntimeException();
		}

		@Override
		public void setEast(IPuzzlePiece p) {
			throw new RuntimeException();
		}

		@Override
		public String getNorthId() {
			throw new RuntimeException();
		}

		@Override
		public String getSouthId() {
			throw new RuntimeException();
		}

		@Override
		public String getEastId() {
			throw new RuntimeException();
		}

		@Override
		public String getWestId() {
			throw new RuntimeException();
		}

		@Override
		public boolean isNRow() {
			throw new RuntimeException();
		}

		@Override
		public boolean isWCol() {
			throw new RuntimeException();
		}

		@Override
		public boolean isSRow() {
			throw new RuntimeException();
		}

		@Override
		public boolean isECol() {
			throw new RuntimeException();
		}

		@Override
		public boolean isNWCorner() {
			throw new RuntimeException();
		}

		@Override
		public boolean isSWCorner() {
			throw new RuntimeException();
		}

		@Override
		public boolean isSECorner() {
			throw new RuntimeException();
		}

		@Override
		public boolean isNECorner() {
			throw new RuntimeException();
		}
	}
	
	public FrozenArrayPuzzle(IPuzzle toFreeze) {
		super(toFreeze.getRows(), toFreeze.getCols());
		int i = 0;
		int j = 0;
		for (Iterator<Iterator<IPuzzlePiece>> rowIt = toFreeze.iterator();
		        rowIt.hasNext();) {
			for (Iterator<IPuzzlePiece> colIt = rowIt.next();
			        colIt.hasNext();) {
				put(new StringPuzzlePiece(colIt.next().getCharacter()), i, j);
				j++;
			}
			j = 0;
			i++;
		}
		solved = true;
	}
	@Override
	public void solve() throws MissingPiecesException {
		// Frozen puzzle is read-only;
		throw new RuntimeException();
	}
	@Override
	public void addPiece(IPuzzlePiece p) {
		// Frozen puzzle is read-only;
		throw new RuntimeException();
	}

}
