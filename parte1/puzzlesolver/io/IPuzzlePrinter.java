package puzzlesolver.io;

import java.io.IOException;

import puzzlesolver.core.IPuzzle;
import puzzlesolver.core.PuzzleNotSolvedException;

/**
 * Classes implementing PuzzlePrinter provide capabilities to output
 * puzzles *somehow* (e.g. text file, console, a nice SVG drawing...).
 * Output format is up to the implementing class.
 */
public interface IPuzzlePrinter {
	void print(IPuzzle pieceSet) throws IOException, PuzzleNotSolvedException;
}