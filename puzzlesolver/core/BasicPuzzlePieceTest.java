package puzzlesolver.core;

import org.junit.Test;

public class BasicPuzzlePieceTest {

	@Test
	public void testConstructor() {
		PuzzlePiece foo;
		foo = new BasicPuzzlePiece("foo", 'f', "n", "s", "w", "e");
	}

	@Test
	public void testSinglePiece() {
		PuzzlePiece foo;
		foo = new BasicPuzzlePiece("foo", 'f', "VUOTO", "VUOTO", "VUOTO", "VUOTO");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDuplicate() {
		PuzzlePiece foo;
		foo = new BasicPuzzlePiece("foo", 'f', "bar", "VUOTO", "VUOTO", "bar");
	}

	@Test
	public void testNW() {
		PuzzlePiece foo;

		foo = new BasicPuzzlePiece("foo", 'f', "VUOTO", "bar", "VUOTO", "baz");
		assert(foo.isNWCorner());
		assert(!foo.isSECorner());

		foo = new BasicPuzzlePiece("foo", 'f',  "bar", "VUOTO", "baz", "VUOTO");
		assert(!foo.isNWCorner());
		assert(foo.isSECorner());

		foo = new BasicPuzzlePiece("foo", 'f',  "VUOTO", "VUOTO", "VUOTO", "VUOTO");
		assert(foo.isNWCorner());
		assert(foo.isSECorner());
	}


}
