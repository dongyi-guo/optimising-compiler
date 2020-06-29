# Task 5

Task 5 eliminates the dead code which is making no difference while the code is compiling

## Steps:

1. `cd Expr`
2. `antlr4 Expr.g4`
3. `javac Expr*.java`
4. `grun Expr main ../task_5_testcases/<filename> && cd ..`
At this point there should be a `blocks` file generated
5. `cd task_5_func && mkdir out`
5. `javac -d out src/*.java`
6. `java -cp out DeadCodeElimination blocks`

This should generate a optimised output.
