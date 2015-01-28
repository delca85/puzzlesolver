JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		puzzlesolver/core/BasicPuzzlePiece.java \
		puzzlesolver/core/HashmapPuzzle.java \
		puzzlesolver/core/ArrayPuzzle.java \
		puzzlesolver/core/ConcurrentHashmapPuzzle.java \
		puzzlesolver/io/PlaintextPuzzlePrinter.java \
		puzzlesolver/io/MalformedFileException.java \
		puzzlesolver/core/MissingPiecesException.java \
		puzzlesolver/core/IPuzzle.java \
		puzzlesolver/io/PuzzleFileParser.java \
		puzzlesolver/core/IPuzzlePiece.java \
		puzzlesolver/io/IPuzzlePrinter.java \
		puzzlesolver/server/IRemotePuzzle.java \
		puzzlesolver/server/RemoteHashmapPuzzle.java \
		puzzlesolver/server/FreezableHashmapPuzzle.java \
		puzzlesolver/server/FrozenArrayPuzzle.java \
		puzzlesolver/PuzzleSolverServer.java \
		puzzlesolver/PuzzleSolverClient.java

default:	PuzzleSolverServer.jar PuzzleSolverClient.jar
PuzzleSolverServer.jar: $(CLASSES:.java=.class)
	jar cvfe PuzzleSolverServer.jar puzzlesolver.PuzzleSolverServer puzzlesolver/*.class puzzlesolver/io/*.class puzzlesolver/core/*.class puzzlesolver/server/*.class

PuzzleSolverClient.jar: $(CLASSES:.java=.class)
	jar cvfe PuzzleSolverClient.jar puzzlesolver.PuzzleSolverClient puzzlesolver/*.class puzzlesolver/io/*.class puzzlesolver/core/*.class puzzlesolver/server/*.class

javadoc:
	javadoc puzzlesolver puzzlesolver.io puzzlesolver.core -d doc 

clean:	
	$(RM) -R *.jar puzzlesolver/*.class puzzlesolver/server/*.class puzzlesolver/client/*.class puzzlesolver/io/*.class puzzlesolver/core/*.class doc/puzzlesolver/* doc/resources/* doc/*
