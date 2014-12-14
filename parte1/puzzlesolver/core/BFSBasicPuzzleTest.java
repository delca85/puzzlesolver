package puzzlesolver.core;

import org.junit.Test;
import java.util.Iterator;

public class BFSBasicPuzzleTest {

	@Test
	public void testConstructor() {
		Puzzle foo;
		foo = new BFSBasicPuzzle();
	}

	@Test
	public void testLoading() {
		/*
		 *  A1 A2
		 *  B1 B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", 'a', "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", 'A', "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", 'b', "A1", "VUOTO", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", 'B', "A2", "VUOTO", "B1", "VUOTO");

		Puzzle foo = new BFSBasicPuzzle();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B1);
		foo.addPiece(B2);
	}

	@Test
	public void testCount() throws MissingPiecesException, PuzzleNotSolvedException {
		/*
		 *  A1 A2
		 *  B1 B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", 'a', "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", 'A', "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", 'b', "A1", "VUOTO", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", 'B', "A2", "VUOTO", "B1", "VUOTO");

		Puzzle foo = new BFSBasicPuzzle();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B1);
		foo.addPiece(B2);
		foo.solve();
		assert(foo.getCols() == 2);
		assert(foo.getRows() == 2);
	}

	@Test
	public void testGetSolution() throws MissingPiecesException, PuzzleNotSolvedException {
		/*
		 *  A1 A2
		 *  B1 B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", 'a', "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", 'A', "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", 'b', "A1", "VUOTO", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", 'B', "A2", "VUOTO", "B1", "VUOTO");

		Puzzle foo = new BFSBasicPuzzle();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B1);
		foo.addPiece(B2);
		foo.solve();
		foo.getSolution();
	}


	@Test(expected = PuzzleNotSolvedException.class)
	public void testPuzzleNotSolved() throws MissingPiecesException, PuzzleNotSolvedException {
		/*
		 *  A1 A2
		 *  B1 B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", 'a', "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", 'A', "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", 'b', "A1", "VUOTO", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", 'B', "A2", "VUOTO", "B1", "VUOTO");

		Puzzle foo = new BFSBasicPuzzle();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B1);
		foo.addPiece(B2);

		foo.getSolution();
	}

	@Test(expected = MissingPiecesException.class)
	public void testMissing() throws MissingPiecesException, PuzzleNotSolvedException {
		/*
		 *  A1 A2
		 *  XX B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", 'a', "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", 'A', "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", 'B', "A2", "VUOTO", "B1", "VUOTO");

		Puzzle foo = new BFSBasicPuzzle();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B2);
		foo.solve();
		Iterator<Iterator<PuzzlePiece>> it = foo.iterator();
	}

	@Test
	public void testMissingThenAdd() throws MissingPiecesException, PuzzleNotSolvedException {
		/*
		 *  A1 A2
		 *  XX B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", 'a', "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", 'A', "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", 'b', "A1", "VUOTO", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", 'B', "A2", "VUOTO", "B1", "VUOTO");

		Puzzle foo = new BFSBasicPuzzle();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B2);
		boolean thrown = false;
		try {
			foo.solve();
		} catch (MissingPiecesException e) {
			thrown = true;
		}
		assert(thrown == true);

		foo.addPiece(B1);
		String output = "";
		foo.solve();
		for (Iterator<Iterator<PuzzlePiece>> rowIt = foo.iterator();
		        rowIt.hasNext();) {
			for (Iterator<PuzzlePiece> colIt = rowIt.next();
			        colIt.hasNext();) {
				output += colIt.next();
			}
			output += "\n";
		}

		assert(output.equals(("" + A1 + A2) + "\n" + ("" + B1 + B2) + "\n"));
	}

	@Test
	public void biggerTestSolve() throws MissingPiecesException, PuzzleNotSolvedException {
		/*
		 *  A1 A2 A3
		 *  B1 B2 B3
		 *  C1 C2 C3
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", 'a', "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", 'A', "VUOTO", "B2", "A1", "A3");
		PuzzlePiece A3 = new BasicPuzzlePiece("A3", '4', "VUOTO", "B3", "A2", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", 'b', "A1", "C1", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", 'B', "A2", "C2", "B1", "B3");
		PuzzlePiece B3 = new BasicPuzzlePiece("B3", '3', "A2", "C3", "B2", "VUOTO");
		PuzzlePiece C1 = new BasicPuzzlePiece("C1", 'c', "B1", "VUOTO", "VUOTO", "C2");
		PuzzlePiece C2 = new BasicPuzzlePiece("C2", 'C', "B2", "VUOTO", "C1", "C3");
		PuzzlePiece C3 = new BasicPuzzlePiece("C3", '6', "B3", "VUOTO", "C2", "VUOTO");

		Puzzle foo = new BFSBasicPuzzle();
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
		Iterator<Iterator<PuzzlePiece>> it = foo.iterator();
	}

	@Test
	public void testIterate() throws MissingPiecesException, PuzzleNotSolvedException {
		/*
		 *  A1 A2
		 *  B1 B2
		 */
		PuzzlePiece A1 = new BasicPuzzlePiece("A1", 'a', "VUOTO", "B1", "VUOTO", "A2");
		PuzzlePiece A2 = new BasicPuzzlePiece("A2", 'A', "VUOTO", "B2", "A1", "VUOTO");
		PuzzlePiece B1 = new BasicPuzzlePiece("B1", 'b', "A1", "VUOTO", "VUOTO", "B2");
		PuzzlePiece B2 = new BasicPuzzlePiece("B2", 'B', "A2", "VUOTO", "B1", "VUOTO");

		Puzzle foo = new BFSBasicPuzzle();
		foo.addPiece(A1);
		foo.addPiece(A2);
		foo.addPiece(B1);
		foo.addPiece(B2);
		String output = "";
		foo.solve();
		for (Iterator<Iterator<PuzzlePiece>> rowIt = foo.iterator();
		        rowIt.hasNext();) {
			for (Iterator<PuzzlePiece> colIt = rowIt.next();
			        colIt.hasNext();) {
				output += colIt.next();
			}
			output += "\n";
		}
		assert(output.equals("aA\nbB\n"));
	}
}
