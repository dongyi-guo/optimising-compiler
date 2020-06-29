# Task 1

## How to run

1. `cd Expr`

2. `antlr4 Expr.g4`

3. `javac Expr*.java`

4. `grun Expr main ../task_1_testcases/<filename>`

## Testcases

1. calc_undefined
   Expected output: `Line 11  :  Undefined a`
                    `Line 14  :  Undefined a`

2. error-less
   Expected Output: Original input, list of variable names, labels and basic blocks.

3. redefined_labels
   Expected output: `Redefined lable: lhead`
                    `Redefined variable: x`

4. undefined_block
   Expected output: `Line 13  :  Undefined lnotexisted`

5. undefined_int
   Expected output: `Line 11  :  Undefined a`
