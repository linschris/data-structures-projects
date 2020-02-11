import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class AStarSearch implements GraphSearchAlgorithm {
	public Path search(State start, State goal) {
		/*
		 * f(n) = g(n) + h(n)
		 */
    	PathPriorityQueue queue = new PathPriorityQueue();
    	HashSet<State> visited = new HashSet<State>();
    	Path currentPath = new Path(start);
    	queue.add(currentPath, totalCost(currentPath, goal));
    	while(!queue.isEmpty()) {
    		Path curr = queue.poll();
    		if(goal.equals(curr.getLastState())) {
    			return curr;
    		}
    		visited.add(curr.getLastState());
    		List<Action> currActions = curr.getLastState().getActions();
    		for(Action action: currActions) {
    			if(!visited.contains(action.getNextState())) {
    				Path path = new Path(curr, action);
    				queue.add(path, totalCost(path, goal));
    			}
    		}
    	}
    	return null;
    }
    	
    	
	
	public int totalCost(Path path, State goal) {
		return g(path) + h(path, goal);
	}
	public int g(Path path) {
		return path.getCost();
	}
	public int h(Path path, State goal) {
		return path.getLastState().heuristicTo(goal);
	}
}

	
