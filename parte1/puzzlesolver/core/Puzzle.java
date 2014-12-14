package puzzlesolver.core;

import java.util.Iterator;

public interface Puzzle {
	/**
	 * Adds a PuzzlePiece to the puzzle.
	 * Note that this invalidates any previously computed solution, so solve() needs to be called again. 
	 */
	public void addPiece(PuzzlePiece p);
	/**
	 * Computes the solution.
	 * @throws MissingPiecesException if the traversal algorithm gets stuck.
	 */
	public void solve() throws MissingPiecesException;
	/**
	 * Returns a 2D-iterator for the solved puzzle.
	 * @throws PuzzleNotSolvedException if solve() was not called beforehand.
	 */
	public Iterator<Iterator<PuzzlePiece>> iterator();
	/**
	 * Returns the puzzle solution as a string.
	 * @throws PuzzleNotSolvedException if solve() was not called beforehand.
	 */
	public String getSolution() throws PuzzleNotSolvedException;
	/**
	 * Returns # of rows.
	 * @throws PuzzleNotSolvedException if solve() was not called beforehand.
	 */
	public int getRows() throws PuzzleNotSolvedException;
	/**
	 * Returns # of columns.
	 * @throws PuzzleNotSolvedException if solve() was not called beforehand.
	 */
	public int getCols() throws PuzzleNotSolvedException;
}
