package puzzlesolver;

import java.io.IOException;

/**
 * Classes implementing PuzzlePrinter provide capabilities to output
 * puzzles *somehow* (e.g. text file, console, a nice SVG drawing...).
 * Output format is up to the implementing class.  
 */
public interface PuzzlePrinter {
	void print(Puzzle pieceSet) throws IOException, MissingPiecesException;
}