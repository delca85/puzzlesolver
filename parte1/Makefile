JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		puzzlesolver/core/BasicPuzzle.java \
		puzzlesolver/core/BasicPuzzlePiece.java \
		puzzlesolver/io/FilePuzzlePrinter.java \
		puzzlesolver/io/MalformedFileException.java \
		puzzlesolver/core/MissingPiecesException.java \
		puzzlesolver/core/Puzzle.java \
		puzzlesolver/io/PuzzleFileParser.java \
		puzzlesolver/core/PuzzlePiece.java \
		puzzlesolver/io/PuzzlePrinter.java \
		puzzlesolver/PuzzleSolver.java

default: PuzzleSolver.jar

PuzzleSolver.jar: $(CLASSES:.java=.class)
	jar cvfe PuzzleSolver.jar puzzlesolver.PuzzleSolver puzzlesolver/*.class puzzlesolver/io/*.class puzzlesolver/core/*.class
javadoc:
	javadoc puzzlesolver puzzlesolver.io puzzlesolver.core -d doc 
clean:	
	$(RM) *.jar puzzlesolver/*.class puzzlesolver/io/*.class puzzlesolver/core/*.class doc/puzzlesolver/* doc/resources/* doc/*
