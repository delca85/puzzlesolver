JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		puzzlesolver/BasicPuzzle.java \
		puzzlesolver/BasicPuzzlePiece.java \
		puzzlesolver/FilePuzzlePrinter.java \
		puzzlesolver/MalformedFileException.java \
		puzzlesolver/MissingPiecesException.java \
		puzzlesolver/Puzzle.java \
		puzzlesolver/PuzzleFileParser.java \
		puzzlesolver/PuzzlePiece.java \
		puzzlesolver/PuzzlePrinter.java \
		puzzlesolver/PuzzleSolver.java

default: PuzzleSolver.jar

PuzzleSolver.jar: $(CLASSES:.java=.class)
	jar cvfe PuzzleSolver.jar puzzlesolver.PuzzleSolver puzzlesolver/*.class

clean:	
	rm *.jar puzzlesolver/*.class
