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
		try {
			List<String[]> tokenList = PuzzleFileParser.parseFile(srcPath);
			Iterator<String[]> it = tokenList.iterator();
			BasicPieceSet ps = new BasicPieceSet();
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
			ps.solve();
			
			String output = "";
			for (Iterator<Iterator<PuzzlePiece>> rowIt = ps.iterator(); 
				 rowIt.hasNext();){
				for (Iterator<PuzzlePiece> colIt = rowIt.next(); 
					 colIt.hasNext();){
					output += colIt.next();
				}
				output += "\n";
			}
			
			System.out.println(output);
		} catch (MalformedFileException e) {
			System.out.println("Malformed file!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MissingPiecesException e) {
			System.out.println("Pieces seem to be missing from input.");
		} 

	}

}
