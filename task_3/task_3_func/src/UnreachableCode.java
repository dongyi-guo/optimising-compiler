import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnreachableCode {

    public static void main(String[] args) {

        if (args.length < 1) { return; }

        ArrayList<Node> nodes = CFG.build(args[0]);

        // Initialise declarations & examine all statements

        HashSet<String> declarations = new HashSet<>();

        for (Node node : nodes) {
            for (String stmt : node.stmts) {
                extractIdents(stmt, declarations);
            }
        }

        // Figure out reachable basic blocks

        HashSet<Node> reach = new HashSet<>();
        HashSet<Node> work = new HashSet<>();

        work.add(nodes.get(0));

        Node curr;
        while (!work.isEmpty()) {

            curr = work.iterator().next();

            if (curr == null) {
                break;
            }

            work.remove(curr);

            reach.add(curr);

            work.addAll(curr.succs);
            work.remove(reach);
        }


        // Output the optimised μC program

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("../optimised"));

            writer.append("void main() {\n\n");

            for (String d : declarations) {
                writer.append(String.format("\tint %s;\n", d));
            }

            for (Node node : nodes) {

                if (reach.contains(node)) {

                    writer.append("\n");

                    if (node.label != null) {
                        writer.append(node.label).append(":\n");
                    }

                    for (String stmt : node.stmts) {
                        if (stmt == null) { continue; }
                        writer.append("\t").append(stmt).append("\n");
                    }
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
}
