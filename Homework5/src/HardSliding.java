import java.util.Arrays;
import java.util.List;
import java.io.PrintWriter;

public class HardSliding {

    public static int[] getCoord(State state, char target) {
        String[] rows = state.toString().split("\\|");
        for (int r = 0; r < rows.length; r++) {
            String row = rows[r];
            for (int c = 0; c < row.length(); c++) {
                if (row.charAt(c) == target) {
                    int[] coord = {r, c};
                    return coord;
                }
            }
        }
        return null;
    }

    public static boolean passed() {
        SlidingPuzzleState start = new SlidingPuzzleState("867|254|3 1");
        SlidingPuzzleState goal  = new SlidingPuzzleState("123|456|78 ");

        String[] stateStrings = {
            "867|254|3 1",
            "867|254|31 ",
            "867|25 |314",
            "86 |257|314",
            "8 6|257|314",
            "856|2 7|314",
            "856| 27|314",
            " 56|827|314",
            "5 6|827|314",
            "526|8 7|314",
            "526|817|3 4",
            "526|817| 34",
            "526| 17|834",
            "526|1 7|834",
            "526|17 |834",
            "52 |176|834",
            "5 2|176|834",
            " 52|176|834",
            "152| 76|834",
            "152|7 6|834",
            "152|736|8 4",
            "152|736|84 ",
            "152|73 |846",
            "152|7 3|846",
            "152|743|8 6",
            "152|743| 86",
            "152| 43|786",
            "152|4 3|786",
            "1 2|453|786",
            "12 |453|786",
            "123|45 |786",
            "123|456|78 ",
        };

        Path path = (new AStarSearch()).search(start, goal);
        if (path == null) {
            System.out.println("Failed to find a path");
            return false;
        }
        List<State> states = path.getStates();
        if (states.size() != stateStrings.length) {
            System.out.println("Expected path of " + stateStrings.length + " states but got " + states.size());
            return false;
        }
        int index = 0;
        for (State state : states) {
            String expected = stateStrings[index];
            String actual = state.toString();
            if (!expected.equals(actual)) {
                System.out.println("Expected state \"" + expected + "\" but got node \"" + actual + "\"");
                return false;
            }
            index++;
        }

        System.out.println();
        System.out.println("Passed");
        return true;
    }

    public static void main(String[] args) {
        System.out.println(passed());
    }

}
