package puzzlesolver;

import java.util.Iterator;

public interface Puzzle {
	public void addPiece(PuzzlePiece p);
	public void solve() throws MissingPiecesException;
	public Iterator<Iterator<PuzzlePiece>> iterator() throws MissingPiecesException;
	public PuzzlePiece getNWCorner();
	public int getRows() throws MissingPiecesException;
	public int getCols() throws MissingPiecesException;
}
