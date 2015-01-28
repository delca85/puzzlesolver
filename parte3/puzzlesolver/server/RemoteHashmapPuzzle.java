package puzzlesolver.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

import puzzlesolver.core.ConcurrentHashmapPuzzle;
import puzzlesolver.core.IPuzzle;
import puzzlesolver.core.IPuzzlePiece;
import puzzlesolver.core.MissingPiecesException;
import puzzlesolver.core.PuzzleNotSolvedException;


public class RemoteHashmapPuzzle extends UnicastRemoteObject implements IRemotePuzzle {

	private FreezableHashmapPuzzle chp;
	
	public RemoteHashmapPuzzle(ExecutorService xs) throws RemoteException {
		super();
		chp = new FreezableHashmapPuzzle(xs);
	}

	@Override
	public void addPiece(IPuzzlePiece p) throws RemoteException {
		chp.addPiece(p);	
	}

	@Override
	public void solve() throws MissingPiecesException, RemoteException {
		chp.solve();
		
	}

	@Override
	public Iterator<Iterator<IPuzzlePiece>> iterator() throws RemoteException {
		return chp.iterator();
	}

	@Override
	public String getSolution() throws PuzzleNotSolvedException,
			RemoteException {
		return chp.getSolution();
	}

	@Override
	public int getRows() throws PuzzleNotSolvedException, RemoteException {
		return chp.getRows();
	}

	@Override
	public int getCols() throws PuzzleNotSolvedException, RemoteException {
		return chp.getCols();
	}
	
	public String getSolutionRows() throws PuzzleNotSolvedException, RemoteException {
		String output = ""; 
		for (Iterator<Iterator<IPuzzlePiece>> rowIt = chp.iterator();
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
	public IPuzzle getPuzzle() throws RemoteException {
		// TODO Auto-generated method stub
		return chp.getFrozen();
	}
}

