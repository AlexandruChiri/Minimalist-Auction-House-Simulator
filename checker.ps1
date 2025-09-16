cd .\src
javac *.java

$i=0
Do {
    echo "Testul $i"
cat "..\Teste\in\$i.in" | java Main.java > "..\Teste\out\$i.out"
diff (cat "..\Teste\ref\$i.ref") (cat "..\Teste\out\$i.out")
    $i++
    }
While ($i -le 9)

rm *.class
cd ..
read-host “Press ENTER to continue...”
