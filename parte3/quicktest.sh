for x in samples/*; 
do echo "========================"; 
echo $x; 
echo "====================="; 
./puzzlesolver.sh $x /dev/stdout;
done

