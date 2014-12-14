package puzzlesolver.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import puzzlesolver.core.IPuzzle;
import puzzlesolver.core.PuzzleNotSolvedException;
import puzzlesolver.core.IPuzzlePiece;

/**
 * Outputs a solved puzzle to a text file.
 */
public class PlaintextPuzzlePrinter implements IPuzzlePrinter {

	private Path outputPath;
	private final Charset charset = StandardCharsets.UTF_8;

	/**
	 * @param  outputPath the path to write the output to.
	 */
	public PlaintextPuzzlePrinter(Path outputPath) {
		this.outputPath = outputPath;
	}

	public void print(IPuzzle puzzle) throws IOException, PuzzleNotSolvedException {
		BufferedWriter writer = Files.newBufferedWriter(outputPath, charset);
		writer.write(puzzle.getSolution());
		writer.write("\n\n");
		String output = "";
		for (Iterator<Iterator<IPuzzlePiece>> rowIt = puzzle.iterator();
		        rowIt.hasNext();) {
			for (Iterator<IPuzzlePiece> colIt = rowIt.next();
			        colIt.hasNext();) {
				output += colIt.next().getCharacter();
			}
			output += "\n";
		}
		writer.write(output);
		writer.write("\n");
		writer.write(puzzle.getRows() + " " + puzzle.getCols() + "\n");
		writer.close();
	}
}
