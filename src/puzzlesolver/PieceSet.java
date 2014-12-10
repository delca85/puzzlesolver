package puzzlesolver;

public interface PieceSet {
	public void queuePiece(PuzzlePiece p);
	public void solve() throws MissingPiecesException;
	public PieceSetIterator iterator() throws PuzzleNotSolvedException;
	public PuzzlePiece getNWCorner();
}
