for x in samples/*; 
do echo "========================"; 
echo $x; 
echo "====================="; 
./PuzzleSolver $x /dev/stdout;
done

