# Task 4

## To propagate constant and fold constant

1. `cd Expr`
2. `antlr4 Expr.g4`
3. `javac Expr*.java`
4. `grun Expr main ../data/<filename> && cd ..`
At this point there should be a `blocks` file locating in `ConstFolding` directory.
5. `cd ../ConstFolding`
6. `mkdir out && javac -d out/ src/*.java`
7. `java -cp out/ ConstFolding blocks`
