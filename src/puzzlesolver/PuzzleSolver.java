package puzzlesolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class PuzzleSolver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Path srcPath = Paths.get(args[0]);
		Path dstPath = Paths.get(args[1]);
		try {
			List<String[]> tokenList = PuzzleFileParser.parseFile(srcPath);
			Iterator<String[]> it = tokenList.iterator();
			
			BasicPuzzle ps = new BasicPuzzle();
			while (it.hasNext()) {
				String[] tokens = it.next();
				ps.addPiece(new BasicPuzzlePiece(
						tokens[0],
						tokens[1],
						tokens[2],
						tokens[3],
						tokens[4],
						tokens[5]
						));
			}
			
			PuzzlePrinter p = new FilePuzzlePrinter(dstPath);
			p.print(ps);
		} catch (MalformedFileException e) {
			System.out.println("Malformed file!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MissingPiecesException e) {
			System.out.println("Pieces seem to be missing from input.");
		} 

	}

}
