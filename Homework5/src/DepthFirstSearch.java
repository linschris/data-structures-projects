import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

// FIXME add imports here

public class DepthFirstSearch implements GraphSearchAlgorithm {

    public Path search(State start, State goal) {
    	LinkedList<Path> queue = new LinkedList<Path>();
    	HashSet<State> visited = new HashSet<State>();
    	Path currentPath = new Path(start);
    	queue.add(currentPath);
    	while(queue.size() > 0) {
    		currentPath = queue.removeLast();
    		if(goal.equals(currentPath.getLastState())) {
				return currentPath;
			}
    		visited.add(currentPath.getLastState());
    		List<Action> actions = currentPath.getLastState().getActions();
    		for(Action a: actions) {
    			System.out.println(a.toString());
    			if(!visited.contains(a.getNextState())) {
					Path newPath = new Path(currentPath, a);
					queue.add(newPath);
					
    			}
    		}	
    	}
    	return null;
    }
}