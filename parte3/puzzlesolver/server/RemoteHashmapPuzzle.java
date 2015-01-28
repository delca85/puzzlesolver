package puzzlesolver.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

import puzzlesolver.core.IPuzzle;
import puzzlesolver.core.IPuzzlePiece;
import puzzlesolver.core.MissingPiecesException;
import puzzlesolver.core.PuzzleNotSolvedException;


public class RemoteHashmapPuzzle extends UnicastRemoteObject implements IRemotePuzzle {

	private FreezableHashmapPuzzle puzzle;
	
	public RemoteHashmapPuzzle(ExecutorService xs) throws RemoteException {
		super();
		puzzle = new FreezableHashmapPuzzle(xs);
	}

	@Override
	public void addPiece(IPuzzlePiece p) throws RemoteException {
		puzzle.addPiece(p);	
	}

	@Override
	public void solve() throws MissingPiecesException, RemoteException {
		puzzle.solve();
		
	}

	@Override
	public Iterator<Iterator<IPuzzlePiece>> iterator() throws RemoteException {
		return puzzle.iterator();
	}

	@Override
	public String getSolution() throws PuzzleNotSolvedException,
			RemoteException {
		return puzzle.getSolution();
	}

	@Override
	public int getRows() throws PuzzleNotSolvedException, RemoteException {
		return puzzle.getRows();
	}

	@Override
	public int getCols() throws PuzzleNotSolvedException, RemoteException {
		return puzzle.getCols();
	}
	
	public String getSolutionRows() throws PuzzleNotSolvedException, RemoteException {
		String output = ""; 
		for (Iterator<Iterator<IPuzzlePiece>> rowIt = puzzle.iterator();
		        rowIt.hasNext();) {
			for (Iterator<IPuzzlePiece> colIt = rowIt.next();
			        colIt.hasNext();) {
				output += colIt.next().getCharacter();
			}
			output += "\n";
		}
		return output;
	}

	@Override
	public IPuzzle freeze() throws RemoteException {
		return puzzle.freeze();
	}
}

