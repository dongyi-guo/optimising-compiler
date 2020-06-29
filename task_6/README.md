# Task 6

For task 6, a combination of anything before is preferred.

## Steps are

1. `cd Expr`

2. `antlr4 Expr.g4`

3. `javac Expr*.java`

4. `grun Expr main <Your own input> / <testcases>`

5. `cd task_6_func && mkdir out`

6. `javac -d out src/*.java`

7. `java -cp out Unreachable DeadCodeElimination ConstFolding DeadCodeElimination blocks`

8. `dot -Tpdf input.dot > ../output.pdf`

9. `mv optimised .. && cd ..`

## Output

For every input, outputs should be provided are:

1. Basic Blocks at `blocks`

2. CFG at `output.pdf`

3. A optimised file at `optimised`
