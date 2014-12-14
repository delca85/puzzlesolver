package puzzlesolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.List;
/**
 * Usage: java PuzzleSolver input.txt output.txt   
 */
public class PuzzleSolver {

	public static void main(String[] args) {
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
				
				BasicPuzzle ps = new BasicPuzzle();
				
				while (it.hasNext()) {
					PuzzleFileParser.PieceStruct struct = it.next();
					ps.addPiece(new BasicPuzzlePiece(
									struct.id,
									struct.character,
									struct.n,
									struct.s,
									struct.w,
									struct.e
							));
				}
				
				ps.solve();
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
