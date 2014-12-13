package puzzlesolver;

import static org.junit.Assert.*;

import org.junit.Test;

public class BasicPuzzlePieceTest {

	@Test
	public void testConstructor() {
		PuzzlePiece foo;
		foo = new BasicPuzzlePiece("foo", "foo", "n", "s", "w", "e");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIncomplete() {
		PuzzlePiece foo;
		foo = new BasicPuzzlePiece("foo", "foo", "VUOTO", "VUOTO", "VUOTO", "e");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDuplicate() {
		PuzzlePiece foo;
		foo = new BasicPuzzlePiece("foo", "foo", "bar", "VUOTO", "VUOTO", "bar");
	}
	
	@Test
	public void testNW() {
		PuzzlePiece foo;
		
		foo = new BasicPuzzlePiece("foo", "foo", "VUOTO", "bar", "VUOTO", "baz");
		assert(foo.isNWCorner());
		assert(!foo.isSECorner());
		
		foo = new BasicPuzzlePiece("foo", "foo",  "bar", "VUOTO", "baz", "VUOTO");
		assert(!foo.isNWCorner());
		assert(foo.isSECorner());
	}
	
	
}
