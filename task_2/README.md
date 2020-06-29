# Task 2

Task 2 separately makes the basic blocks and the control flow graph.

## Generate Variable names, Labels and Basic Blocks

1. `cd Expr`

2. `antlr4 Expr.g4`

3. `javac Expr*.java`

4. `grun Expr main ../task_2_testcases/<filename> && cd ..`

This will feedback the list of variable names, labels and basic blocks in terminal output and also generate a "blocks" file under directory cfg containing all the basic blocks and our CFG will be made based on it:

## Generate Control Flow Graphs

1. `javac task_2_func/*.java`

2. `cd task_2_func && java CFG ../blocks`

3. `dot -Tpdf input.dot > ../output.pdf && cd..`

This will generate a output.pdf file, containing the control flow graph.

## Testcases

There are 5 testcases:

1. error_less

2. goto_free

3. if_cond_endless

4. never_ends

5. unreacable_block

For each of them, their expected outcomes of basic blocks and control flow graph are located in directory "task_2_testcases" within the subfolders naming with them respectively.
