JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		puzzlesolver/core/BasicPuzzlePiece.java \
		puzzlesolver/core/HashmapPuzzle.java \
		puzzlesolver/core/BFSHashmapPuzzle.java \
		puzzlesolver/io/PlaintextPuzzlePrinter.java \
		puzzlesolver/io/MalformedFileException.java \
		puzzlesolver/core/MissingPiecesException.java \
		puzzlesolver/core/IPuzzle.java \
		puzzlesolver/io/PuzzleFileParser.java \
		puzzlesolver/core/IPuzzlePiece.java \
		puzzlesolver/io/IPuzzlePrinter.java \
		puzzlesolver/PuzzleSolver.java

default: PuzzleSolver.jar

PuzzleSolver.jar: $(CLASSES:.java=.class)
	jar cvfe PuzzleSolver.jar puzzlesolver.PuzzleSolver puzzlesolver/*.class puzzlesolver/io/*.class puzzlesolver/core/*.class
javadoc:
	javadoc puzzlesolver puzzlesolver.io puzzlesolver.core -d doc 
clean:	
	$(RM) *.jar puzzlesolver/*.class puzzlesolver/io/*.class puzzlesolver/core/*.class doc/puzzlesolver/* doc/resources/* doc/*
