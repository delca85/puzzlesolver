package puzzlesolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import puzzlesolver.core.ConcurrentHashmapPuzzle;
import puzzlesolver.core.BasicPuzzlePiece;
import puzzlesolver.core.MissingPiecesException;
import puzzlesolver.core.IPuzzle;
import puzzlesolver.io.PlaintextPuzzlePrinter;
import puzzlesolver.io.MalformedFileException;
import puzzlesolver.io.PuzzleFileParser;
import puzzlesolver.io.IPuzzlePrinter;
/**
 * Usage: java PuzzleSolver input.txt output.txt
 */
public class PuzzleSolver {
	
	final static int N_CPUS = Runtime.getRuntime().availableProcessors();
	final static int POOLSIZE = N_CPUS+1;
	// Goetz, JCIP, §8.2 Sizing Thread Pools
	
	public static void main(String[] args) {
		
		ExecutorService xs = Executors.newFixedThreadPool(POOLSIZE);
		
		if (args.length != 2) {
			System.err.println("Usage: java PuzzleSolver input.txt output.txt");
			return;
		}

		Path srcPath = Paths.get(args[0]);
		Path dstPath = Paths.get(args[1]);

		try {
			List<PuzzleFileParser.PieceStruct> tokenList = PuzzleFileParser.parseFile(srcPath);
			try {
				Iterator<PuzzleFileParser.PieceStruct> it = tokenList.iterator();

				IPuzzle puzzle = new ConcurrentHashmapPuzzle(xs);

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
				printer.print(puzzle);
			} catch (MissingPiecesException e) {
				System.err.println("Pieces seem to be missing from input.");
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
		
		xs.shutdown();
		
	}
}
