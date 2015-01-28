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
		String output = "";
		output += puzzle.getSolution();
		output += "\n\n";
		for (Iterator<Iterator<IPuzzlePiece>> rowIt = puzzle.iterator();
		        rowIt.hasNext();) {
			for (Iterator<IPuzzlePiece> colIt = rowIt.next();
			        colIt.hasNext();) {
				output += colIt.next().getCharacter();
			}
			output += "\n";
		}
		
		output += "\n";
		output += puzzle.getRows() + " " + puzzle.getCols() + "\n";
		
		BufferedWriter writer = Files.newBufferedWriter(outputPath, charset);
		try { 
			writer.write(output);
		} catch (Exception e) {
			throw e;
		} finally {
			writer.close();
		}
	}
}
