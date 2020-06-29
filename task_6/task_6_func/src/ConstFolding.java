import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstFolding {

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

        // Initialise the data flow

        HashMap<String, ArrayList<HashMap<String, String>>> dataFlow = new HashMap<>();
        for (Node node : nodes) {
            ArrayList<HashMap<String, String>> arr = new ArrayList<>();
            arr.add(new HashMap<>()); // in
            arr.add(new HashMap<>()); // out
            dataFlow.put(node.name, arr);
        }

        // Constant propagation

        boolean finished = false;

        while (!finished) {

            finished = true;

            for (Node node : nodes) {

                HashMap<String, String> gen = new HashMap<>();
                HashMap<String, String> in = dataFlow.get(node.name).get(0);
                HashMap<String, String> out = dataFlow.get(node.name).get(1);

                // Update the in set by joining all out sets of predecessors
                for (Node pred : node.preds) {
                    meet(in, dataFlow.get(pred.name).get(1));
                }

                // Examine all statements in the block one by one
                for (String stmt : node.stmts) {
                    updateDataFlow(stmt, gen, in);
                }

                HashMap<String, String> tmpOut = new HashMap<>();
                tmpOut.putAll(in);
                tmpOut.putAll(gen);
//                tmpOut.values().removeIf(Objects::isNull);

                dataFlow.get(node.name).set(0, in);
                dataFlow.get(node.name).set(1, tmpOut);

                finished = finished && sameMap(out, tmpOut);
            }
        }

        // Constant folding

        for (Node node : nodes) {

            HashMap<String, String> gen = new HashMap<>();

            // Remove variables which have multiple possible values
            HashMap<String, String> in = dataFlow.get(node.name).get(0);
            in.values().removeIf(Objects::isNull);

            for (int i = 0; i < node.stmts.size(); i++) {

                String stmt = node.stmts.get(i);

                stmt = simplifyStmt(stmt, gen, in);

                // Put the new statement to the node
                node.stmts.set(i, stmt);
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
     * This function joins the 2nd set to the 1st set.
     * If the key of one entry in the 2nd set appears in the 1st set, the corresponding value is changed to null.
     * Otherwise, the entry is added to the 1st set.
     * @param mapA: List of <String, String> entries.
     * @param mapB: List of <String, String> entries.
     */
    private static void meet(HashMap<String, String> mapA, HashMap<String, String> mapB) {

        for (String key : mapB.keySet()) {
            if (mapA.containsKey(key)) {
                if (mapA.get(key) != null && !mapA.get(key).equals(mapB.get(key))) {
                    mapA.put(key, null);
                }
            } else {
                mapA.put(key, mapB.get(key));
            }
        }
    }

    /**
     * This function can compare two maps to check whether two maps have some entries.
     * Null values are ignored in this function.
     * @param mapA: List of <String, String> entries.
     * @param mapB: List of <String, String> entries.
     * @return true if entries are same, otherwise false.
     */
    private static boolean sameMap(HashMap<String, String> mapA, HashMap<String, String> mapB) {

        HashMap<String, String> cleanedMapA = new HashMap<>();
        cleanedMapA.putAll(mapA);
        cleanedMapA.values().removeIf(Objects::isNull);

        HashMap<String, String> cleanedMapB = new HashMap<>();
        cleanedMapB.putAll(mapB);
        cleanedMapB.values().removeIf(Objects::isNull);

        if (cleanedMapA.size() != cleanedMapB.size()) {
            return false;
        }

        for (String key : cleanedMapB.keySet()) {
            if (!cleanedMapA.containsKey(key)) {
                return false;
            } else {
                if (!cleanedMapA.get(key).equals(cleanedMapB.get(key))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * This function can update the gen set according to the statement.
     * @param stmt: Statement to examine.
     * @param gen: List of variables generated inside the block.
     * @param in: List of vairables given to the block.
     */
    private static void updateDataFlow(String stmt, HashMap<String, String> gen, HashMap<String, String> in) {

        stmt = stmt.substring(0, stmt.length() - 1);

        HashMap<String, String> cleanedIn = new HashMap<>();
        cleanedIn.putAll(in);
        cleanedIn.values().removeIf(Objects::isNull);

        while (true) {

            // 1. Assignment statement
            if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[0-9]+")) {

                String ident = stmt.split("\\s*=\\s*")[0];
                String value = stmt.split("\\s*=\\s*")[1];

                gen.put(ident, value);
                break;
            }

            // 2. y = x
            if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[a-zA-Z_][a-zA-Z0-9_]*")) {

                String ident = stmt.split("\\s*=\\s*")[1];

                if (gen.containsKey(ident)) {
                    stmt = stmt.split("\\s*=\\s*")[0] + " = " + gen.get(ident);
                    continue;
                }

                if (cleanedIn.containsKey(ident)) {
                    stmt = stmt.split("\\s*=\\s*")[0] + " = " + cleanedIn.get(ident);
                    continue;
                }

                break;
            }

            // 3. y = ident/num + ident/num
            if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*([a-zA-Z_][a-zA-Z0-9_]*|[0-9]+)\\s*([+\\-*/<>=])\\s*([a-zA-Z_][a-zA-Z0-9_]*|[0-9]+)")) {

                String[] tokens = stmt.split("\\s+=\\s*");
                String left = tokens[0];
                String right = tokens[1];

                String[] operands = right.split("\\s*([+\\-*/<>=])\\s*");
                String operandA = operands[0];
                String operandB = operands[1];

                // Replace idents with their values
                if (gen.containsKey(operandA)) {
                    operandA = gen.get(operandA);
                } else if (cleanedIn.containsKey(operandA)) {
                    operandA = cleanedIn.get(operandA);
                }

                if (gen.containsKey(operandB)) {
                    operandB = gen.get(operandB);
                } else if (cleanedIn.containsKey(operandB)) {
                    operandB = cleanedIn.get(operandB);
                }

                String operator = null;
                if (right.contains("+")) {
                    operator = "+";
                    if (operandA.equals("0")) {
                        operandA = null;
                    }
                    if (operandB.equals("0")) {
                        operandB = null;
                    }
                } else if (right.contains("-")) {
                    operator = "-";
                    if (operandB.equals("0")) {
                        operandB = null;
                    }
                } else if (right.contains("*")) {
                    operator = "*";
                    if (operandA.equals("0") || operandB.equals("0")) {
                        operandA = null;
                        operandB = null;
                    } else if (operandA.equals("1")) {
                        operandA = null;
                    } else if (operandB.equals("1")) {
                        operandB = null;
                    }
                } else if (right.contains("/")) {
                    operator = "/";
                    if (operandA.equals("0")) {
                        operandA = null;
                        operandB = null;
                    } else if (operandB.equals("1")) {
                        operandB = null;
                    }
                } else if (right.contains("<")) {
                    operator = "<";
                } else if (right.contains(">")) {
                    operator = ">";
                } else if (right.contains("=")) {
                    operator = "=";
                }

                if (operandA == null && operandB == null) {
                    stmt = left + " = 0";
                    continue;
                } else if (operandA == null) {
                    stmt = left + " = " + operandB;
                    continue;
                } else if (operandB == null) {
                    stmt = left + " = " + operandA;
                    continue;
                } else {
                    if (!operandA.matches("\\d+") || !operandB.matches("\\d+")) {
                        break;
                    }
                    stmt = String.format("%s = %s %s %s", left, operandA, operator, operandB);
                }
            }

            // 3. res = num + num
            if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[0-9]+\\s*([+\\-*/<>=])\\s*[0-9]+")) {

                String[] tokens = stmt.split("\\s*=\\s*", 2);
                String left = tokens[0];
                String right = tokens[1];

                String[] nums = right.split("\\s*([+\\-*/<>=])\\s*");
                int operandA = Integer.parseInt(nums[0]);
                int operandB = Integer.parseInt(nums[1]);

                int res = 0;
                if (right.contains("+")) {
                    res = operandA + operandB;
                } else if (right.contains("-")) {
                    res = operandA - operandB;
                } else if (right.contains("*")) {
                    res = operandA * operandB;
                } else if (right.contains("/")) {
                    res = operandA / operandB;
                } else if (right.contains("<")) {
                    res = (operandA < operandB) ? 1 : 0;
                } else if (right.contains(">")) {
                    res = (operandA > operandB) ? 1 : 0;
                } else if (right.contains("=")) {
                    res = (operandA == operandB) ? 1 : 0;
                }

                stmt = left + " = " + res;
                continue;
            }

            break;
        }
    }

    /**
     * This function can simplify a statement according to variables in gen and in.
     * @param stmt: Statement to be simplified.
     * @param gen: List of data generated inside the block.
     * @param in: List of data given to the block.
     * @return the simplified statement.
     */
    private static String simplifyStmt(String stmt, HashMap<String, String> gen, HashMap<String, String> in) {

        stmt = stmt.substring(0, stmt.length() - 1);

        while (true) {

            // 1. Assignment statement
            if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[0-9]+")) {

                String ident = stmt.split("\\s*=\\s*")[0];
                String value = stmt.split("\\s*=\\s*")[1];

                gen.put(ident, value);
                break;
            }

            // 2. y = x
            if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[a-zA-Z_][a-zA-Z0-9_]*")) {

                String ident = stmt.split("\\s*=\\s*")[1];

                if (gen.containsKey(ident)) {
                    stmt = stmt.split("\\s*=\\s*")[0] + " = " + gen.get(ident);
                    continue;
                }

                if (in.containsKey(ident)) {
                    stmt = stmt.split("\\s*=\\s*")[0] + " = " + in.get(ident);
                    continue;
                }

                break;
            }

            // 3. y = ident/num + ident/num
            if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*([a-zA-Z_][a-zA-Z0-9_]*|[0-9]+)\\s*([+\\-*/<>=])\\s*([a-zA-Z_][a-zA-Z0-9_]*|[0-9]+)")) {

                String[] tokens = stmt.split("\\s+=\\s*");
                String left = tokens[0];
                String right = tokens[1];

                String[] operands = right.split("\\s*([+\\-*/<>=])\\s*");
                String operandA = operands[0];
                String operandB = operands[1];

                // Replace idents with their values
                if (gen.containsKey(operandA)) {
                    operandA = gen.get(operandA);
                } else if (in.containsKey(operandA)) {
                    operandA = in.get(operandA);
                }
                if (gen.containsKey(operandB)) {
                    operandB = gen.get(operandB);
                } else if (in.containsKey(operandB)) {
                    operandB = in.get(operandB);
                }

                String operator = null;
                if (right.contains("+")) {
                    operator = "+";
                    if (operandA.equals("0")) {
                        operandA = null;
                    }
                    if (operandB.equals("0")) {
                        operandB = null;
                    }
                } else if (right.contains("-")) {
                    operator = "-";
                    if (operandB.equals("0")) {
                        operandB = null;
                    }
                } else if (right.contains("*")) {
                    operator = "*";
                    if (operandA.equals("0") || operandB.equals("0")) {
                        operandA = null;
                        operandB = null;
                    } else if (operandA.equals("1")) {
                        operandA = null;
                    } else if (operandB.equals("1")) {
                        operandB = null;
                    }
                } else if (right.contains("/")) {
                    operator = "/";
                    if (operandA.equals("0")) {
                        operandA = null;
                        operandB = null;
                    } else if (operandB.equals("1")) {
                        operandB = null;
                    }
                } else if (right.contains("<")) {
                    operator = "<";
                } else if (right.contains(">")) {
                    operator = ">";
                } else if (right.contains("=")) {
                    operator = "=";
                }

                if (operandA == null && operandB == null) {
                    stmt = left + " = 0";
                    continue;
                } else if (operandA == null) {
                    stmt = left + " = " + operandB;
                    continue;
                } else if (operandB == null) {
                    stmt = left + " = " + operandA;
                    continue;
                } else {
                    stmt = String.format("%s = %s %s %s", left, operandA, operator, operandB);
                    if (!operandA.matches("\\d+") || !operandB.matches("\\d+")) {
                        break;
                    }
                }
            }

            // 3. res = num + num
            if (stmt.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[0-9]+\\s*([+\\-*/<>=])\\s*[0-9]+")) {

                String[] tokens = stmt.split("\\s*=\\s*", 2);
                String left = tokens[0];
                String right = tokens[1];

                String[] nums = right.split("\\s*([+\\-*/<>=])\\s*");
                int operandA = Integer.parseInt(nums[0]);
                int operandB = Integer.parseInt(nums[1]);

                int res = 0;
                if (right.contains("+")) {
                    res = operandA + operandB;
                } else if (right.contains("-")) {
                    res = operandA - operandB;
                } else if (right.contains("*")) {
                    res = operandA * operandB;
                } else if (right.contains("/")) {
                    res = operandA / operandB;
                } else if (right.contains("<")) {
                    res = (operandA < operandB) ? 1 : 0;
                } else if (right.contains(">")) {
                    res = (operandA > operandB) ? 1 : 0;
                } else if (right.contains("=")) {
                    res = (operandA == operandB) ? 1 : 0;
                }

                stmt = left + " = " + res;
                continue;
            }

            // 4. if (ident) goto
            if (stmt.matches("if\\([a-zA-Z_][a-zA-Z0-9_]*\\)[\\s\\S]*")) {

                String cond = stmt.split("[()]")[1];

                if (gen.containsKey(cond)) {
                    stmt = stmt.replace("(" + cond + ")", "(" + gen.get(cond) + ")");
                } else if (in.containsKey(cond)) {
                    stmt = stmt.replace("(" + cond + ")", "(" + in.get(cond) + ")");
                } else {
                    break;
                }
            }

            // 5. if (num) goto
            if (stmt.matches("if\\([0-9]+\\)[\\s\\S]*")) {
                String[] tokens = stmt.split("[\\s]*\\([\\s]*|[\\s]*\\)[\\s]*");
                int cond = Integer.parseInt(tokens[1]);
                if (cond > 0) {
                    stmt = tokens[2];
                } else {
                    return null;
                }
                break;
            }

            // 6. print(ident)
            if (stmt.matches("print\\([a-zA-Z_][a-zA-Z0-9_]*\\)")) {
                String target = stmt.split("[()]")[1];
                if (gen.containsKey(target)) {
                    stmt = stmt.replace("(" + target + ")", "(" + gen.get(target) + ")");
                } else if (in.containsKey(target)) {
                    stmt = stmt.replace("(" + target + ")", "(" + in.get(target) + ")");
                }
                break;
            }

            break;
        }

        return stmt + ";";
    }
}