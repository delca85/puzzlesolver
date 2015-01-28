package puzzlesolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
/**
 * Usage: java PuzzleSolver input.txt output.txt
 */
public class PuzzleClient {
	
	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.err.println("Usage: java PuzzleSolver input.txt output.txt server");
			return;
		}

		Path srcPath = Paths.get(args[0]);
		Path dstPath = Paths.get(args[1]);
		String serverName = args[2];
		
		try {
			List<PuzzleFileParser.PieceStruct> tokenList = PuzzleFileParser.parseFile(srcPath);
			try {
				Iterator<PuzzleFileParser.PieceStruct> it = tokenList.iterator();
				IRemotePuzzle puzzle = (IRemotePuzzle)Naming.lookup("rmi://"+serverName+":5000/puzzle");  
				
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

				puzzle.solve();
				IPuzzlePrinter printer = new PlaintextPuzzlePrinter(dstPath);
				IPuzzle serialized = puzzle.getPuzzle();
				System.out.println(puzzle.getSolution());
				printer.print(serialized);
			} catch (MissingPiecesException e) {
				System.err.println("Pieces seem to be missing from input.");
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IllegalArgumentException e) {
			System.err.println("A puzzle piece appears to be invalid.");
		} catch (MalformedFileException e) {
			System.err.println("Could not parse - malformed file!");
		} catch (NoSuchFileException e) {
			System.err.println("File not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
