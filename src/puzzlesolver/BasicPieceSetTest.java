package puzzlesolver;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Iterator;

public class BasicPieceSetTest {

	@Test
	public void testConstructor() {
		PieceSet foo;
		foo = new BasicPieceSet();
	}
	
	@Test
	public void testLoading() {
		/*
		 *  A1 A2
		 *  B1 B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", "A1", "VUOTO", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", "A2", "VUOTO", "B1", "VUOTO");
		
		PieceSet foo = new BasicPieceSet();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B1);
		foo.addPiece(B2);
	}
	
	@Test
	public void testSolve() throws MissingPiecesException {
		/*
		 *  A1 A2
		 *  B1 B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", "A1", "VUOTO", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", "A2", "VUOTO", "B1", "VUOTO");
		
		PieceSet foo = new BasicPieceSet();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B1);
		foo.addPiece(B2);
		foo.solve();
	}
	
	@Test(expected = MissingPiecesException.class)
	public void testMissing() throws MissingPiecesException {
		/*
		 *  A1 A2
		 *  XX B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", "A2", "VUOTO", "B1", "VUOTO");
		
		PieceSet foo = new BasicPieceSet();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B2);
		foo.solve();
	}
	
	@Test
	public void biggerTestSolve() throws MissingPiecesException {
		/*
		 *  A1 A2 A3
		 *  B1 B2 B3
		 *  C1 C2 C3
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", "VUOTO", "B2", "A1", "A3");
		PuzzlePiece A3 = new BasicPuzzlePiece("A3", "VUOTO", "B3", "A2", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", "A1", "C1", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", "A2", "C2", "B1", "B3");
		PuzzlePiece B3 = new BasicPuzzlePiece("B3", "A2", "C3", "B2", "VUOTO");
		PuzzlePiece C1 = new BasicPuzzlePiece("C1", "B1", "VUOTO", "VUOTO", "C2");
		PuzzlePiece C2 = new BasicPuzzlePiece("C2", "B2", "VUOTO", "C1", "C3");
		PuzzlePiece C3 = new BasicPuzzlePiece("C3", "B3", "VUOTO", "C2", "VUOTO");
		
		PieceSet foo = new BasicPieceSet();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(A3);
		foo.addPiece(B1);
		foo.addPiece(B2);
		foo.addPiece(B3);
		foo.addPiece(C1);
		foo.addPiece(C2);
		foo.addPiece(C3);
		foo.solve();
	}

	@Test
	public void testIterate() throws MissingPiecesException, PuzzleNotSolvedException {
		/*
		 *  A1 A2
		 *  B1 B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", "A1", "VUOTO", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", "A2", "VUOTO", "B1", "VUOTO");
		
		PieceSet foo = new BasicPieceSet();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B1);
		foo.addPiece(B2);
		foo.solve();
		String output = "";
		for (Iterator<Iterator<PuzzlePiece>> rowIt = foo.iterator(); 
			 rowIt.hasNext();){
			for (Iterator<PuzzlePiece> colIt = rowIt.next(); 
				 colIt.hasNext();){
				output += colIt.next();
			}
			output += "\n";
		}
		
		assert (output.equals(("" + A1 + A2) + "\n" + ("" + B1 + B2) + "\n"));
	}
}
