grammar Expr;

// Import possible used Java libraries
@parser::header{
    import java.util.*;
    import java.io.*;
    import java.lang.*;
}
main

    /* Declare the variables helping on rule evaluation
        ncheck: Variable name validation check
        cl: List of code lines
        counter: Counter of blocks
        dcounter: counter of declarations
        code: Map of block and its segments
        is_first: Check the first statement
        i: List of defined variable names
        n: List of defined labels
        u: Map of used labels and their located lines
        d: Map for all the names' re-definition checks
    */
    locals [
        int ncheck = 0,
        List<String> cl = new ArrayList<String>(),
        int counter = 0,
        int dcounter = 0,
        HashMap<Integer,List<String>> code = new HashMap<Integer,List<String>>(),
        boolean is_first = true,
        List<String> i = new ArrayList<String>(),
        List<String> n = new ArrayList<String>(),
        HashMap<Integer,String> u = new HashMap<Integer,String>(),
        HashMap<String,Integer> d = new HashMap<String,Integer>()
    ]
    : 'void' 'main' '(' ')' '{' (declaration)* (statements)* '}'
    {
        /*
            Label checking
            
            check the used labels with comparing them to defined labels,
            if there is a undeclared one, report the error with its line.
            and set label check boolean to false, which will suppress further output.
         */
        int lcheck = 0;
        for(HashMap.Entry<Integer,String> entry: $main::u.entrySet()){
            int found = 0;
            for(String is:$main::i){
                if(is.equals(entry.getValue())){
                    found = 1;
                }
            }
            if(found == 0){
                lcheck = 1;
                System.err.println("Line " + entry.getKey() + ": Undefiend " + entry.getValue());
            }
        }

        /*
            Re-definition 

            uses the d hashmap respectively checks variabel names and labels with their declaration times,
            counter of which will raise if another declaration of the same name is found,
            this will report the error and modify the check booleans to suppress further output.
         */
        for(String is : $main::i){
            if($main::d.containsKey(is)){
                $main::d.put(is,$main::d.get(is) + 1);
            }else{
                $main::d.put(is,1);
            }
        }
        for(HashMap.Entry<String,Integer> entry : $main::d.entrySet()){
            if(entry.getValue() > 1){
                lcheck = 1;
                System.out.println("Redefined label: " + entry.getKey());
            }
        }
        $main::d.clear();
        for(String is : $main::n){
            if($main::d.containsKey(is)){
                $main::d.put(is,$main::d.get(is) + 1);
            }else{
                $main::d.put(is,1);
            }
        }
        for(HashMap.Entry<String,Integer> entry : $main::d.entrySet()){
            if(entry.getValue() > 1){
                $main::ncheck = 1;
                System.out.println("Redefined variable: " + entry.getKey());
            }
        }

        /*
            Dead declaration remove
            
            This will examine declaraed variable and check if thery are used in statements.
            if not, eliminate its existence.
        */
        int dead = 0;
        for (String i : $main::n){
            for (int ctr = $main::dcounter; ctr < $main::cl.size(); ctr++){
                if($main::cl.get(ctr).contains(i)){
                    dead = 1;
                }
            }
            if(dead == 0){
                for(int c = 0; c < $main::cl.size(); c++){
                    if($main::cl.get(c).contains(i)){
                    $main::cl.remove(c);
                    }
                }
            }   
        }

        /*
            Output the input, list of variable names and basic blocks

            if all the label check and variable name check pass,
            output the original input, output list of both labels and variables
            and basic blocks, also basic blocks will be passed to a plain text file for CFG production.
         */
        if(($main::ncheck == 0) && (lcheck == 0)){
            System.out.println("void main() {");
            for(String here : $main::cl){
                System.out.println(here);
            }
            System.out.println("}");

            System.out.print("Defined variable(s): ");
            for(String a:$main::n){
                System.out.print(a + " ");
            }
            System.out.println();
            
            System.out.print("Defined lables(s): ");
            for(String a:$main::i){
                System.out.print(a + " ");
            }
            System.out.println();

            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter("../blocks"));
                for(HashMap.Entry<Integer,List<String>> entry: $main::code.entrySet()){
                    System.out.println("Block " + entry.getKey() + " : " + entry.getValue());
                    writer.append("B" + entry.getKey());
                    writer.append(" ");
                    writer.append(Arrays.toString(entry.getValue().toArray()));
                    writer.append("\n");
                }
                writer.close();
            }catch (IOException e){}
        }
    }
    ;

// Add variable name to name list and add the code line to list of code line.
declaration: 'int' IDENT {$main::n.add($IDENT.text);} ';' {$main::cl.add("int " + $IDENT.text + ";"); $main::dcounter++;}
    ;

/*
    For each statement rules, do these things:

    1. Check whether the statement is the first one, if so, proceed it into a basic block(could only be B0). After then set the boolean to false.
    2. Add statements to code line.
    3. Add declared labels to list of them.
    4. Add used labels and their lines to map of them.
    5. If this line is a label declaration, immediately create a new basic block and pass it in.
    6. If this line has a goto, pass this rule to the current basic block, and generate a new block.
    7. Otherwise, pass the rule into the current basic block.
    8. For variables in statement, examine whether they are in the declared variable name list, if not report the error with the name and its lines.
 */
statements:
    {
        if(($main::is_first == false)){
            if($main::code.get($main::counter) != null){
                $main::counter++;
            }
        }

        if($main::is_first == true){
            $main::is_first = false;
        }
    }
    IDENT 
    {
        if($main::is_first == true){
            $main::is_first = false;
        }
        $main::i.add($IDENT.text);
        $main::code.computeIfAbsent($main::counter,k -> new ArrayList<>()).add($IDENT.text + ":");
    } ':' {$main::cl.add($IDENT.text + ":");}
    | IDENT '=' calc 
    {
        if($main::is_first == true){
            $main::is_first = false;
        }
        $main::code.computeIfAbsent($main::counter,k -> new ArrayList<>()).add($IDENT.text + " = " + $calc.text + ";");
        if(!$main::n.contains($IDENT.text)){
            $main::ncheck = 1;
            System.err.println("Line " + $IDENT.line +   ": Undefined " + $IDENT.text);
        }
    } ';' {$main::cl.add($IDENT.text + " = " + $calc.text);}
    | 'print' '(' IDENT ')' ';'
    {
        $main::cl.add("print(" + $IDENT.text + ") ;");
        if($main::is_first == true){
            $main::is_first = false;
        }
        $main::code.computeIfAbsent($main::counter,k -> new ArrayList<>()).add("print(" + $IDENT.text + ");");
        if(!$main::n.contains($IDENT.text)){
            $main::ncheck = 1;
            System.err.println("Line " + $IDENT.line +   ": Undefined " + $IDENT.text);
        }
    }
    | 'print' '(' NUM ')' ';'
    {
        $main::cl.add("print(" + $NUM.text + ") ;");
        if($main::is_first == true){
            $main::is_first = false;
        }
        $main::code.computeIfAbsent($main::counter,k -> new ArrayList<>()).add("print(" + $NUM.text + ");");
    }
    | 'goto' IDENT ';'
    {
        $main::cl.add("goto " + $IDENT.text + ";");
        if($main::is_first == true){
            $main::is_first = false;
        }
        if(!$main::i.contains($IDENT.text)){
            $main::u.put($IDENT.line,$IDENT.text);
        }
        
        if($main::is_first == false){
            $main::code.computeIfAbsent($main::counter++,k -> new ArrayList<>()).add("goto " + $IDENT.text + ";");
        }else{
            $main::code.computeIfAbsent($main::counter,k -> new ArrayList<>()).add("goto " + $IDENT.text + ";");
        }
    }
    | 'if' '(' element ')' 'goto' IDENT ';'
    {
        $main::cl.add("if (" + $element.text + ") goto " + $IDENT.text + ";");
        if($main::is_first == true){
            $main::is_first = false;
        }
        if(!$main::i.contains($IDENT.text)){
            $main::u.put($IDENT.line,$IDENT.text);
        }
        
        if($main::is_first == false){
            $main::code.computeIfAbsent($main::counter++,k -> new ArrayList<>()).add("if(" + $element.text + ") goto " + $IDENT.text + ";");
        }else{
            $main::code.computeIfAbsent($main::counter,k -> new ArrayList<>()).add("if(" + $element.text + ") goto " + $IDENT.text + ";");
        }
    }
    ;

calc: element
    | element '+' element
    | element '-' element
    | element '*' element
    | element '/' element
    | element '<' element
    | element '>' element
    | element '=' element
    ;

// Examine whether they are in the declared variable name list, if not report the error with the name and its lines.
element: IDENT
    {
        if(!$main::n.contains($IDENT.text)){
            $main::ncheck = 1;
            System.err.println("Line " + $IDENT.line +   ": Undefined " + $IDENT.text);
        }
    }
    | NUM
    ;

IDENT: [a-zA-Z_][a-zA-Z0-9]*;
NUM: [0-9]+ ;
WS: [ \r\n\t]+ -> skip;