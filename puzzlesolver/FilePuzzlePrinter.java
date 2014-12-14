package puzzlesolver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Outputs a solved puzzle to a text file.   
 */
public class FilePuzzlePrinter implements PuzzlePrinter {
	
	private Path outputPath;
	private final Charset charset = StandardCharsets.UTF_8;
	
	/**
	 * @param  outputPath the path to write the output to.   
	 */
	FilePuzzlePrinter(Path outputPath) {
		this.outputPath = outputPath;
	}

	public void print(Puzzle puzzle) throws IOException, PuzzleNotSolvedException {
		BufferedWriter writer = Files.newBufferedWriter(outputPath, charset);
		writer.write(puzzle.getSolution());
		writer.write("\n\n");
		String output = "";
		for (Iterator<Iterator<PuzzlePiece>> rowIt = puzzle.iterator(); 
			 rowIt.hasNext();){
			for (Iterator<PuzzlePiece> colIt = rowIt.next(); 
				 colIt.hasNext();){
				output += colIt.next().getCharacter();
			}
			output += "\n";
		}			 
		writer.write(output);
		writer.write("\n");
		writer.write(puzzle.getRows()+" "+puzzle.getCols()+"\n");
		writer.close();
	}
}
