# Task 3

Task 3 optimises the program in the based on the blocks generated from the expression rule file,

and it removes any unreachable blocks.

## Generate Basic Blocks

1. `cd Expr`

2. `antlr4 Expr.g4`

3. `javac Expr*.java`

4. `grun Expr main ../task_3_testcases/<filename> && cd ..`

This will feedback the list of variable names, labels and basic blocks in terminal output and also generate a "blocks" file under directory cfg containing all the basic blocks and our CFG will be made based on it:

## Remove the Unreachable Blocks

1. `cd task_3_func && mkdir out`

2. `javac -d out src/*.java`

3. `java -cp out UnreachableCode ../blocks`

This will generate a "optimised" file which contains the code refined.

## Testcases

There are 5 testcases:

1. error_less

2. goto_free

3. if_cond_endless

4. unreacable_l2

5. unreacable_block

For each of them, their optimised blocks are located in directory "task_3_output" within the subfolders naming with them respectively.
