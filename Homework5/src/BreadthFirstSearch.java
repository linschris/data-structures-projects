import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

// FIXME add imports here

public class BreadthFirstSearch implements GraphSearchAlgorithm {

    public Path search(State start, State goal) {
    	LinkedList<Path> queue = new LinkedList<Path>();
    	HashSet<State> visited = new HashSet<State>();
    	Path currentPath = new Path(start);
    	queue.add(currentPath);
    	while(queue.size() > 0) {
    		currentPath = queue.pop();
    		visited.add(currentPath.getLastState());
    		List<Action> actions = currentPath.getLastState().getActions();
    		for(Action a: actions) {
    			if(!visited.contains(a.getNextState())) {
					Path newPath = new Path(currentPath, a);
					if(goal.equals(newPath.getLastState())) {
	    				return newPath;
	    			}
					else {
						queue.add(newPath);
					}
    			}
    		}	
    	}
    	return null;
    }
}