import java.util.List;
import java.util.Iterator;

public class Main {

    public static void search(GraphSearchAlgorithm algo, State start, State goal) {
        Path path = algo.search(start, goal);
        if (path == null) {
            System.out.println("Path not found."); 
            return;// FIXME print something
        }
        Iterator<State> stateIter = path.getStates().iterator();
        Iterator<Action> actionIter = path.getActions().iterator();
        stateIter.next().print();
        System.out.println();
        while (stateIter.hasNext() && actionIter.hasNext()) {
            System.out.println("Action: " + actionIter.next());
            System.out.println();
            stateIter.next().print();
            System.out.println();
        }
    }

    public static void testMaze() {
        /* The maze is:
        +---+
        |  E|
        |S# |
        |   |
        +---+
        */
    	
    	/* New maze is:
    	 * 
    	 * +----+
    	 * |   E|
    	 * |    |
    	 * | ## |
    	 * |S  	|
    	 * +----+
    	 * 
    	 */

        MazeState start = new MazeState("   |*# |   ");
        MazeState goal  = new MazeState("  *| # |   ");
       
    	//MazeState start = new MazeState("    |    |####|*   ");
    	//MazeState goal  = new MazeState("   *|    |####|    ");
        System.out.println("==================");
        System.out.println("Depth First Search");
        System.out.println("==================");
        System.out.println();
        search(new DepthFirstSearch(), start, goal);
	
        System.out.println("====================");
        System.out.println("Breadth First Search");
        System.out.println("====================");
        System.out.println();
        search(new BreadthFirstSearch(), start, goal);
        
        System.out.println("=========");
        System.out.println("A* Search");
        System.out.println("=========");
        System.out.println();
        search(new AStarSearch(), start, goal);
        
    }

    public static void testSlidingPuzzle() {

        /* The sliding puzzle is:
        +---+
        |182|
        | 43|
        |765|
        +---+
        */

        SlidingPuzzleState start = new SlidingPuzzleState("182| 43|765");
        SlidingPuzzleState goal  = new SlidingPuzzleState("123|456|78 ");

        System.out.println("=========");
        System.out.println("A* Search");
        System.out.println("=========");
        System.out.println();
        search(new AStarSearch(), start, goal);
    }

    public static void main(String[] args) {
        testMaze();
        testSlidingPuzzle();
    }

}