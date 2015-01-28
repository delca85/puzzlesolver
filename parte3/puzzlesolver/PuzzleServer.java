package puzzlesolver;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import puzzlesolver.server.IRemotePuzzle;
import puzzlesolver.server.RemoteHashmapPuzzle;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Usage: java PuzzleSolver servername
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
