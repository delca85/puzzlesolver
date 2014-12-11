default: puzzlesolver
puzzlesolver: 
	mkdir bin && javac -d bin/ -sourcepath src/ src/puzzlesolver/PuzzleSolver.java
clean: 
	rm -R bin/
