package puzzlesolver;

import java.util.Iterator;

public interface Puzzle {
	public void addPiece(PuzzlePiece p);
	public Iterator<Iterator<PuzzlePiece>> iterator() throws MissingPiecesException;
	public String getSolution() throws MissingPiecesException;
	public int getRows() throws MissingPiecesException;
	public int getCols() throws MissingPiecesException;
}
