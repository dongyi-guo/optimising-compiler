import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadCodeElimination {

    public static void main(String[] args) {

        if (args.length < 1) { return; }

        ArrayList<Node> nodes = CFG.build(args[0]);

        // Create end node, generate edges to the end node, add the end node to the graph

        Node end = new Node("end");

        for (Node node : nodes) {
            if (node.succs.size() == 0) {
                node.succs.add(end);
                end.preds.add(node);
            }
        }

        nodes.add(end);

        HashMap<String, ArrayList<HashSet<String>>> dataFlow = new HashMap<>();
        for (Node node : nodes) {
            ArrayList<HashSet<String>> arr = new ArrayList<>();
            arr.add(new HashSet<>()); // in
            arr.add(new HashSet<>()); // out
            dataFlow.put(node.name, arr);
        }

        // DFA

        boolean finished = false;

        while (!finished) {

            finished = true;

            for (Node node : nodes) {

                HashSet<String> def = new HashSet<>();
                HashSet<String> use = new HashSet<>();
                HashSet<String> in = dataFlow.get(node.name).get(0);
                HashSet<String> out = dataFlow.get(node.name).get(1);

                // Update the in set by joining all out sets of predecessors
                for (Node succ : node.succs) {
                    meet(out, dataFlow.get(succ.name).get(0));
                }

                // Examine all statements in the block one by one
                for (String stmt : node.stmts) {
                    updateDataFlow(stmt, def, use);
                }

                HashSet<String> tmpIn = new HashSet<>(out);
                tmpIn.removeAll(def);
                tmpIn.addAll(use);

                dataFlow.get(node.name).set(0, tmpIn);
                dataFlow.get(node.name).set(1, out);

                finished = finished && sameSet(in, tmpIn);
            }
        }

        // Eliminate dead codes

        for (Node node : nodes) {

            // Remove variables which have multiple possible values
            HashSet<String> out = dataFlow.get(node.name).get(1);

            for (int i = node.stmts.size() - 1; i >= 0; i--) {

                String stmt = node.stmts.get(i);

                stmt = checkStmt(stmt, out);

                // Put the new statement to the node
                node.stmts.set(i, stmt);
            }
        }

        // Initialise declarations & examine all statements

        HashSet<String> declarations = new HashSet<>();

        for (Node node : nodes) {
            for (String stmt : node.stmts) {
                extractIdents(stmt, declarations);
            }
        }

        // Output the optimised μC program

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("optimised"));

            writer.append("void main() {\n\n");

            for (String d : declarations) {
                writer.append(String.format("\tint %s;\n", d));
            }

            for (Node node : nodes) {

                writer.append("\n");

                if (node.label != null) {
                    writer.append(node.label).append(":\n");
                }

                for (String stmt : node.stmts) {
                    if (stmt == null) { continue; }
                    writer.append("\t").append(stmt).append("\n");
                }
            }

            writer.append("}\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function extracts all identifications in a statement.
     * @param stmt: Statement to examine.
     * @param declarations: Declaration made in the program.
     */
    private static void extractIdents(String stmt, HashSet<String> declarations) {

        if (stmt == null || declarations == null) { return; }

        if (!stmt.contains("=")) { return; }

        Pattern pattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
        Matcher matcher = pattern.matcher(stmt);

        while(matcher.find()) {
            declarations.add(matcher.group());
        }
    }

    /**
     * This function adds all elements in the 2nd set to the 1st set.
     * @param setA: Set of identities.
     * @param setB: Set of identities.
     */
    private static void meet(HashSet<String> setA, HashSet<String> setB) {
        setA.addAll(setB);
    }

    /**
     * This function can update the def set and the use set according to the statement.
     * @param stmt: Statement to examine.
     * @param def: Def set.
     * @param use: Use set.
     */
    private static void updateDataFlow(String stmt, HashSet<String> def, HashSet<String> use) {

        stmt = stmt.substring(0, stmt.length() - 1);

        if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[\\s\\S]*")) {
            def.add(stmt.split("\\s*=\\s*")[0]);
        }

        if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[a-zA-Z_][a-zA-Z0-9_]*")) {
            use.add(stmt.split("\\s*=\\s*")[1]);
            return;
        }

        if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*([a-zA-Z_][a-zA-Z0-9_]*|[0-9]+)\\s*([+\\-*/<>=])\\s*([a-zA-Z_][a-zA-Z0-9_]*|[0-9]+)")) {

            String expr = stmt.split("\\s*=\\s*", 2)[1];

            Pattern pattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
            Matcher matcher = pattern.matcher(expr);

            while(matcher.find()) {
                use.add(matcher.group());
            }

            return;
        }

        if (stmt.matches("if\\([a-zA-Z_][a-zA-Z0-9_]*\\)[\\s\\S]*")) {
            use.add(stmt.split("[()]")[1]);
            return;
        }

        if (stmt.matches("print\\([a-zA-Z_][a-zA-Z0-9_]*\\)")) {
            use.add(stmt.split("[()]")[1]);
            return;
        }
    }

    /**
     * This function checks whether two sets have same entries.
     * @param mapA: Set of identities.
     * @param mapB: Set of identities.
     * @return true if sets are the same, false otherwise.
     */
    private static boolean sameSet(HashSet<String> mapA, HashSet<String> mapB) {
        return mapA != null && mapB != null
                && mapA.size() == mapB.size()
                && mapA.containsAll(mapB);
    }

    /**
     * This function can check whether the statement should be eliminated.
     * At the same time, it also updates the out set.
     * @param stmt: Statement to examine.
     * @param out: Out set for the basic block.
     * @return original statement if it should not be eliminated, null otherwise.
     */
    private static String checkStmt(String stmt, HashSet<String> out) {

        stmt = stmt.substring(0, stmt.length() - 1);

        // Eliminate

        if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[\\s\\S]*")) {
            if (!out.contains(stmt.split("\\s*=\\s*")[0])) {
                return null;
            }
        }

        if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[a-zA-Z_][a-zA-Z0-9_]*")) {
            out.add(stmt.split("\\s*=\\s*")[1]);
        }

        if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*([a-zA-Z_][a-zA-Z0-9_]*|[0-9]+)\\s*([+\\-*/<>=])\\s*([a-zA-Z_][a-zA-Z0-9_]*|[0-9]+)")) {

            String expr = stmt.split("\\s*=\\s*", 2)[1];

            Pattern pattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
            Matcher matcher = pattern.matcher(expr);

            while(matcher.find()) {
                out.add(matcher.group());
            }
        }

        if (stmt.matches("if\\([a-zA-Z_][a-zA-Z0-9_]*\\)[\\s\\S]*")) {
            out.add(stmt.split("[()]")[1]);
        }

        if (stmt.matches("print\\([a-zA-Z_][a-zA-Z0-9_]*\\)")) {
            out.add(stmt.split("[()]")[1]);
        }

        return stmt + ";";
    }
}
