package puzzlesolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import puzzlesolver.core.BasicPuzzlePiece;
import puzzlesolver.core.MissingPiecesException;
import puzzlesolver.core.IPuzzle;
import puzzlesolver.io.PlaintextPuzzlePrinter;
import puzzlesolver.io.MalformedFileException;
import puzzlesolver.io.PuzzleFileParser;
import puzzlesolver.io.IPuzzlePrinter;
import puzzlesolver.server.IRemotePuzzle;

/**
 * Usage: java PuzzleClient input.txt output.txt server
 */

public class PuzzleSolverClient {

	final static int MAX_RETRIES = 5;

	public static void main(String[] args) {

		if (args.length != 3) {
			System.err.println("Usage: java PuzzleSolver input.txt output.txt server");
			return;
		}

		Path srcPath = Paths.get(args[0]);
		Path dstPath = Paths.get(args[1]);
		String serverName = args[2];
		String rmiAddress = "rmi://"+serverName+":5000/puzzle";
		try {
			List<PuzzleFileParser.PieceStruct> tokenList = PuzzleFileParser.parseFile(srcPath);
			IRemotePuzzle puzzle = (IRemotePuzzle)Naming.lookup(rmiAddress);

			try {
				Iterator<PuzzleFileParser.PieceStruct> it = tokenList.iterator();

				while (it.hasNext()) {
					PuzzleFileParser.PieceStruct struct = it.next();
					puzzle.addPiece(new BasicPuzzlePiece(
					                struct.id,
					                struct.character,
					                struct.n,
					                struct.s,
					                struct.w,
					                struct.e
					            ));
				}

				class ExponentialBackoff {
					public void backoffSolve(IRemotePuzzle puzzle) throws RemoteException, MissingPiecesException {
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
					
					public IPuzzle backoffGetPuzzle(IRemotePuzzle puzzle) throws RemoteException {
						int i = 0;
						while (i < MAX_RETRIES) {
							try { 
								return puzzle.getPuzzle();
							} catch (RemoteException e) {
								try {
									Thread.sleep(2^i * 100);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
							}
							i++;
						}
						return puzzle.getPuzzle();
					}
				}
				
				try {
					(new ExponentialBackoff()).backoffSolve(puzzle);
				} catch (RemoteException e) {
					System.err.println("Connection error, couldn't solve puzzle after "+MAX_RETRIES+" attempts.");
					return;
				}

				IPuzzle frozen;
				
				try {
					frozen = (new ExponentialBackoff()).backoffGetPuzzle(puzzle);
				} catch (RemoteException e) {
					System.err.println("Connection error, couldn't retrieve solved puzzle after "+MAX_RETRIES+" attempts.");
					return;
				}
				
				IPuzzlePrinter printer = new PlaintextPuzzlePrinter(dstPath);
				printer.print(frozen);
				
			} catch (MissingPiecesException e) {
				System.err.println("Pieces seem to be missing from input.");
			}
		} catch (NotBoundException e) {
			System.err.println("Could not look up "+rmiAddress);
		} catch (IllegalArgumentException e) {
			System.err.println("A puzzle piece appears to be invalid.");
		} catch (MalformedFileException e) {
			System.err.println("Could not parse - malformed file!");
		} catch (NoSuchFileException e) {
			System.err.println("File not found!");
		} catch (ConnectException e) {
			System.err.println("ConnectException while trying to connect to RMI resource "+rmiAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
