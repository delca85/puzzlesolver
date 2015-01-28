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
public class PuzzleSolverServer {
	
	final static int N_CPUS = Runtime.getRuntime().availableProcessors();
	final static int POOLSIZE = N_CPUS+1;
	final static int RMI_PORT = 1099;
	// Goetz, JCIP, §8.2 Sizing Thread Pools

	public static void main(String[] args) {

		ExecutorService xs = Executors.newFixedThreadPool(POOLSIZE);

		if (args.length != 1) {
			System.err.println("Usage: java PuzzleServer servername");
			return;
		}

		String rmiAddress = "rmi://"+args[0]+":"+RMI_PORT+"/puzzle";

		try{  
			IRemotePuzzle puzzle = new RemoteHashmapPuzzle(xs);  
			Naming.rebind(rmiAddress, puzzle);  
	        System.out.println("Server ready");
		} catch (RemoteException e) {
			System.err.println("RemoteException while trying to bind to "+rmiAddress);
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.err.println("Malformed URL:"+rmiAddress);
		}
	}
}
