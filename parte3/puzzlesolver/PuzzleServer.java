package puzzlesolver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import puzzlesolver.core.BasicPuzzlePiece;
import puzzlesolver.core.MissingPiecesException;
import puzzlesolver.core.IPuzzle;
import puzzlesolver.io.PlaintextPuzzlePrinter;
import puzzlesolver.io.MalformedFileException;
import puzzlesolver.io.PuzzleFileParser;
import puzzlesolver.io.IPuzzlePrinter;
import puzzlesolver.server.IRemotePuzzle;
import puzzlesolver.server.RemoteHashmapPuzzle;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Usage: java PuzzleSolver input.txt output.txt
 */
public class PuzzleServer {
	
	final static int N_CPUS = Runtime.getRuntime().availableProcessors();
	final static int POOLSIZE = N_CPUS+1;
	// Goetz, JCIP, §8.2 Sizing Thread Pools
	
	public static void main(String[] args) {
		
		ExecutorService xs = Executors.newFixedThreadPool(POOLSIZE);
		
		
		
		if (args.length != 1) {
			System.err.println("Usage: java PuzzleServer servername");
			return;
		}

		try{  
			IRemotePuzzle puzzle = new RemoteHashmapPuzzle(xs);  
			Naming.rebind("rmi://"+args[0]+":5000/puzzle", puzzle);  
	        System.err.println("Server ready");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
