package puzzlesolver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class FilePuzzlePrinter implements PuzzlePrinter {
	
	private Path outputPath;
	private final Charset charset = StandardCharsets.UTF_8;
	
	FilePuzzlePrinter(Path outputPath) {
		this.outputPath = outputPath;
	}

	public void print(Puzzle puzzle) throws IOException, MissingPiecesException {
		BufferedWriter writer = Files.newBufferedWriter(outputPath, charset);
		writer.write(puzzle.getSolution());
		writer.write("\n\n");
		String output = "";
		for (Iterator<Iterator<PuzzlePiece>> rowIt = puzzle.iterator(); 
			 rowIt.hasNext();){
			for (Iterator<PuzzlePiece> colIt = rowIt.next(); 
				 colIt.hasNext();){
				output += colIt.next();
			}
			output += "\n";
		}			 
		writer.write(output);
		writer.write("\n");
		writer.write(puzzle.getRows()+" "+puzzle.getCols()+"\n");
		writer.close();
	}
}
