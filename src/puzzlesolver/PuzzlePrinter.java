package puzzlesolver;

import java.io.IOException;

public interface PuzzlePrinter {
	void print(Puzzle pieceSet) throws IOException, MissingPiecesException;
}