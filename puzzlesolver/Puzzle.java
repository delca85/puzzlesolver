package puzzlesolver;

import java.util.Iterator;

public interface Puzzle {
	/**
	 * Adds a PuzzlePiece to the puzzle.
	 */
	public void addPiece(PuzzlePiece p);
	/**
	 * Returns a 2D-iterator for the solved puzzle.
	 */
	public Iterator<Iterator<PuzzlePiece>> iterator() throws MissingPiecesException;
	/**
	 * Returns the puzzle solution as a string.
	 * No guarantee re:complexity as it might have to solve the puzzle 
	 * depending on implementation.
	 */
	public String getSolution() throws MissingPiecesException;
	/**
	 * Returns # of rows.
	 */
	public int getRows() throws MissingPiecesException;
	/**
	 * Returns # of columns.
	 */
	public int getCols() throws MissingPiecesException;
}
