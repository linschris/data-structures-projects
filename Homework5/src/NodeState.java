import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.TreeMap;

class NodeState extends State {

    class Edge {
        int src;
        int dst;
        int cost;

        public Edge(int src, int dst, int cost) {
            this.src = src;
            this.dst = dst;
            this.cost = cost;
        }
    }

    private int currNode;
    private TreeMap<Integer, Integer> heuristics;
    private TreeMap<Integer, LinkedList<Edge>> edges;

    public NodeState(String s) {
        this.heuristics = new TreeMap<Integer, Integer>();
        this.edges = new TreeMap<Integer, LinkedList<Edge>>();
        String[] lines = s.split("\\|");
        int num_nodes = 0;
        int num_edges = 0;
        this.currNode = Integer.parseInt(lines[0]);
        for (int lineNum = 1; lineNum < lines.length; lineNum++) {
            String line = lines[lineNum];
            String[] parts = line.split(":");
            String[] srcString = parts[0].split(" ");
            int src = Integer.parseInt(srcString[0]);
            int heuristic = Integer.parseInt(srcString[1].substring(1, srcString[1].length() - 1));
            this.heuristics.put(src, heuristic);
            if (!this.edges.containsKey(src)) {
                this.edges.put(src, new LinkedList<Edge>());
            }

            if (parts.length == 1) {
                continue;
            }
            for (String dstStrings : parts[1].split(", ")) {
                if (dstStrings.startsWith(" ")) {
                    dstStrings = dstStrings.substring(1);
                }
                String[] dstString = dstStrings.split(" ");
                int dst = Integer.parseInt(dstString[0]);
                int cost = Integer.parseInt(dstString[1].substring(1, dstString[1].length() - 1));
                this.edges.get(src).add(new Edge(src, dst, cost));
            }
        }
    }

    public List<Action> getActions() {
        LinkedList<Action> actions = new LinkedList<Action>();
        List<Edge> outEdges = this.edges.get(this.currNode);
        if (outEdges != null) {
            for (Edge edge : this.edges.get(this.currNode)) {
                actions.add(new Action(
                    edge.src + "->" + edge.dst + " (" + edge.cost + ")",
                    edge.cost,
                    new NodeState(this.toString(edge.dst))
                ));
            }
        }
        return actions;
    }

    public int heuristicTo(State goal) {
        return this.heuristics.get(this.currNode);
    }

    private String getGraphString() {
        String result = "";
        for (Map.Entry<Integer, LinkedList<Edge>> entry : this.edges.entrySet()) {
            int src = entry.getKey();
            LinkedList<Edge> outEdges = entry.getValue();
            result += "|" + src + " (" + this.heuristics.get(src) + "):";
            if (outEdges.size() > 0) {
                String edgeString = "";
                for (Edge outEdge : outEdges) {
                    edgeString += ", " + outEdge.dst + " (" + outEdge.cost + ")";
                }
                result += edgeString.substring(1);
            }
        }
        return result.substring(1);
    }
    
    private String toString(int node) {
        return node + "|" + this.getGraphString();
    }

    public String toString() {
        return this.toString(this.currNode);
    }

    public void print() {
        LinkedList<String> lines = new LinkedList<String>();
        lines.add("// Visualize at http://viz-js.com/");
        lines.add("digraph {");
        for (Map.Entry<Integer, LinkedList<Edge>> entry : this.edges.entrySet()) {
            int src = entry.getKey();
            if (src == this.currNode) {
                lines.add("  " + src + " [label=\"" + src + " (" + this.heuristics.get(src) + ")\" style=filled, fillcolor=\"#C0C0C0\"]");
            } else {
                lines.add("  " + src + " [label=\"" + src + " (" + this.heuristics.get(src) + ")\"]");
            }
            LinkedList<Edge> outEdges = entry.getValue();
            for (Edge outEdge : outEdges) {
                lines.add("  " + src + " -> " + outEdge.dst + " [label=\"" + outEdge.cost + "\"]");
            }
        }
        lines.add("}");
        for (String line : lines) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) {
        String[] strings = {
            "1",
            "1 (1): 2 (1), 3 (1), 4 (1)",
            "1 (1): 5 (1)",
            "2 (1): 6 (1)",
            "3 (1): 6 (1)",
            "4 (1): 6 (1)",
            "5 (1): 6 (1)",
        };
        String graphString = "";
        for (String list : strings) {
            graphString += "|" + list;
        }
        graphString = graphString.substring(1);
        NodeState graph = new NodeState(graphString);
        System.out.println(graph);
        graph.print();
    }
}
