package puzzlesolver.client;

import java.rmi.RemoteException;

import puzzlesolver.core.IPuzzle;
import puzzlesolver.core.IPuzzlePiece;
import puzzlesolver.core.MissingPiecesException;
import puzzlesolver.server.IRemotePuzzle;

public class ExponentialBackoffPuzzleWrapper {
	final int MAX_RETRIES;
	IRemotePuzzle puzzle;
	
	public ExponentialBackoffPuzzleWrapper(IRemotePuzzle puzzle, int MAX_RETRIES) {
		this.puzzle = puzzle;
		this.MAX_RETRIES = MAX_RETRIES;
	}
	
	public void backoffSolve() throws RemoteException, MissingPiecesException {
		int i = 0;
		while (i < MAX_RETRIES) {
			try { 
				puzzle.solve();
				return;
			} catch (RemoteException e) {
				try {
					Thread.sleep(2^i * 100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			i++;
		}
		puzzle.solve();
	}
	
	public IPuzzle backoffFreeze() throws RemoteException {
		int i = 0;
		while (i < MAX_RETRIES) {
			try { 
				return puzzle.freeze();
			} catch (RemoteException e) {
				try {
					Thread.sleep(2^i * 100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			i++;
		}
		return puzzle.freeze();
	}
	
	public void backoffAddPiece(IPuzzlePiece piece) throws RemoteException {
		int i = 0;
		while (i < MAX_RETRIES) {
			try { 
				puzzle.addPiece(piece);
			} catch (RemoteException e) {
				try {
					Thread.sleep(2^i * 100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			i++;
		}
		puzzle.addPiece(piece);
	}
}
