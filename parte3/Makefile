JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		puzzlesolver/core/BasicPuzzlePiece.java \
		puzzlesolver/core/HashmapPuzzle.java \
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
		puzzlesolver/PuzzleServer.java \
		puzzlesolver/PuzzleClient.java

default:	PuzzleServer.jar PuzzleClient.jar
PuzzleServer.jar: $(CLASSES:.java=.class)
	jar cvfe PuzzleServer.jar puzzlesolver.PuzzleServer puzzlesolver/*.class puzzlesolver/io/*.class puzzlesolver/core/*.class

PuzzleClient.jar: $(CLASSES:.java=.class)
	jar cvfe PuzzleClient.jar puzzlesolver.PuzzleClient puzzlesolver/*.class puzzlesolver/io/*.class puzzlesolver/core/*.class

javadoc:
	javadoc puzzlesolver puzzlesolver.io puzzlesolver.core -d doc 

clean:	
	$(RM) -R *.jar puzzlesolver/*.class puzzlesolver/server/*.class puzzlesolver/client/*.class puzzlesolver/io/*.class puzzlesolver/core/*.class doc/puzzlesolver/* doc/resources/* doc/*
