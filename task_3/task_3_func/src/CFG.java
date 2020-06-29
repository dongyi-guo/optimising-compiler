import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

class CFG {

    static ArrayList<Node> build(String filename) {

        if (filename == null) { return null; }

        ArrayList<String[]> basicBlocks;
        ArrayList<Node> nodes;

        basicBlocks = readFileByLines(filename);
        nodes = generateNodes(basicBlocks);
        generateEdges(basicBlocks, nodes);

        return nodes;
    }

    /**
     * This function reads basic blocks from the specified file,
     * then split each block into tokens and store them in a 2D array.
     * @param fileName：the file name that we will read basic blocks from
     * @return a 2D array containing all basic blocks
     */
    private static ArrayList<String[]> readFileByLines(String fileName) {

        File file = new File(fileName);
        BufferedReader reader;
        ArrayList<String[]> basicBlocks = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("[\\s]*,[\\s]*|[\\s]*\\[[\\s]*|[\\s]*][\\s]*");
                basicBlocks.add(tokens);
            }

            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return basicBlocks;
    }

    /**
     * This function creates nodes for blocks and stores generated nodes in a 2D array.
     * @param basicBlocks: a 2D array containing basic blocks
     * @return a 2D array containging all nodes in the control flow graph
     */
    private static ArrayList<Node> generateNodes(ArrayList<String[]> basicBlocks) {

        ArrayList<Node> nodes = new ArrayList<>();

        for (String[] tokens : basicBlocks) {

            Node node = new Node(tokens[0]);

            boolean labeled = tokens[1].charAt((tokens[1].length() - 1)) == ':';

            if (labeled) {
                node.label = tokens[1].substring(0, tokens[1].length() - 1);
            }

            node.stmts.addAll(Arrays.asList(tokens).subList((labeled ? 2 : 1), tokens.length));

            nodes.add(node);
        }

        return nodes;
    }

    /**
     * This function can generate edges among nodes according to basic blocks.
     * @param basicBlocks: Basic blocks
     * @param nodes: List of nodes for basic blocks
     */
    private static void generateEdges(ArrayList<String[]> basicBlocks, ArrayList<Node> nodes) {

        boolean conJump = false; //is this block includes if statement
        boolean unconJump = false; //is this block includes goto statements
        String target = "";   // if has goto statement, which label will goto

        for (int i = 0; i < basicBlocks.size(); i++) {

            Node curr = nodes.get(i);

            for (String stmt : curr.stmts) {

                conJump = stmt.contains("if");

                unconJump = !conJump && stmt.contains("goto");

                if (conJump || unconJump) {
                    target = stmt.split("goto\\s*|;")[1];
                }
            }

            if (conJump) {

                for (Node node : nodes) {
                    if (node.label != null && node.label.equals(target)) {
                        curr.succs.add(node);
                        node.preds.add(curr);
                    }
                }

                if (i < nodes.size() - 1) {
                    curr.succs.add(nodes.get(i + 1));
                    nodes.get(i + 1).preds.add(curr);
                }

            } else if (unconJump) {

                for (Node node : nodes) {
                    if (node.label != null && node.label.equals(target)) {
                        curr.succs.add(node);
                        node.preds.add(curr);
                        break;
                    }
                }
            } else {
                if (i < nodes.size() - 1) {
                    curr.succs.add(nodes.get(i + 1));
                    nodes.get(i + 1).preds.add(curr);
                }
            }
        }
    }

    public static void main(String[] args) {
    }
}
