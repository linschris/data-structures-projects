import java.util.List;
import java.io.PrintWriter;

public class SmallAStar {

    public static String join(String[] strings) {
        String result = "";
        for (String string : strings) {
            result += "|" + string;
        }
        return result.substring(1);
    }

    public static int getNode(State state) {
        return Integer.parseInt(state.toString().split("\\|")[0]);
    }

    public static boolean passed() {
        String[] strings = {
            "1 (3): 2 (1), 3 (1)",
            "2 (3): 4 (1), 7 (4)",
            "3 (2): 1 (1), 5 (2)",
            "4 (1): 6 (1)",
            "5 (1): 1 (0), 7 (2)",
            "6 (1): 1 (0), 7 (1)",
            "7 (0):"
        };
        String graphString = join(strings);
        NodeState start = new NodeState("1|" + graphString);
        NodeState goal = new NodeState("7|" + graphString);

        System.out.println("Graph:");
        start.print();
        System.out.println();
        System.out.println("Goal node: " + getNode(goal));
        System.out.println();

        Path path = (new AStarSearch()).search(start, goal);
        if (path == null) {
            System.out.println("Failed to find a path");
            return false;
        }
        List<State> states = path.getStates();
        int[] stateIDs = {1, 2, 4, 6, 7};
        if (states.size() != stateIDs.length) {
            System.out.println("Expected path of " + stateIDs.length + " states but got " + states.size());
            return false;
        }
        int index = 0;
        for (State state : states) {
            int expected = stateIDs[index];
            int actual = getNode(state);
            if (expected != actual) {
                System.out.println("Expected node " + expected + " but got node " + actual);
                return false;
            }
            index++;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(passed());
    }

}
