package puzzlesolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.List;

public class PuzzleSolver {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java PuzzleSolver input.txt output.txt");
			return;
		}
	
		Path srcPath = Paths.get(args[0]);
		Path dstPath = Paths.get(args[1]);
		
		try {
			List<String[]> tokenList = PuzzleFileParser.parseFile(srcPath);
			try {
				Iterator<String[]> it = tokenList.iterator();
				
				BasicPuzzle ps = new BasicPuzzle();
				while (it.hasNext()) {
					String[] tokens = it.next();
					ps.addPiece(new BasicPuzzlePiece(
							tokens[0],
							tokens[1],
							tokens[2],
							tokens[4],
							tokens[5],
							tokens[3]
							));
				}
				
				PuzzlePrinter p = new FilePuzzlePrinter(dstPath);
				p.print(ps);
			} catch (MalformedFileException e) {
				System.err.println("Malformed file!");
			} catch (MissingPiecesException e) {
				System.err.println("Pieces seem to be missing from input.");
			}
		} catch (NoSuchFileException e) {
			System.err.println("File not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
