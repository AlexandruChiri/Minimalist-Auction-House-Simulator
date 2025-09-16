#!/bin/bash

cd src
javac *.java

for i in {0..9};
do
	echo "Testul $i"
	cat "../Teste/in/$i.in" | java Main > "../Teste/out/$i.out"
	diff "../Teste/ref/$i.ref" "../Teste/out/$i.out"
done

rm *.class
cd ..
