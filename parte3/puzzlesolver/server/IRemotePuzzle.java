package puzzlesolver.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

import puzzlesolver.core.IPuzzle;
import puzzlesolver.core.IPuzzlePiece;
import puzzlesolver.core.MissingPiecesException;
import puzzlesolver.core.PuzzleNotSolvedException;
/**
 * Represent a puzzle.
 * The target program flow must be:
 * - addPiece(...)
 * - ... 
 * - addPiece(...)
 * - solve()
 * - getSolution() or iterator() or getRows();
 */

public interface IRemotePuzzle extends Remote { 
	/**
	 * Adds a IPuzzlePiece to the puzzle.
	 * Note that this invalidates any previously computed solution, so solve() needs to be called again. 
	 */
	public void addPiece(IPuzzlePiece p) throws RemoteException;
	/**
	 * Computes the solution.
	 * @throws MissingPiecesException if the traversal algorithm gets stuck.
	 */
	public void solve() throws MissingPiecesException, RemoteException;
	/**
	 * Returns a 2D-iterator for the solved puzzle.
	 * @throws PuzzleNotSolvedException if solve() was not called beforehand.
	 */
	public Iterator<Iterator<IPuzzlePiece>> iterator() throws RemoteException;
	/**
	 * Returns the puzzle solution as a string.
	 * @throws PuzzleNotSolvedException if solve() was not called beforehand.
	 */
	public String getSolution() throws PuzzleNotSolvedException, RemoteException;
	/**
	 * Returns # of rows.
	 * @throws PuzzleNotSolvedException if solve() was not called beforehand.
	 */
	public int getRows() throws PuzzleNotSolvedException, RemoteException;
	/**
	 * Returns # of columns.
	 * @throws PuzzleNotSolvedException if solve() was not called beforehand.
	 */
	public int getCols() throws PuzzleNotSolvedException, RemoteException;
	
	public IPuzzle getPuzzle() throws RemoteException;
}