import java.lang.Comparable;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class PathPriorityQueue {

    class Priority implements Comparable<Priority> {

        Path path;
        int priority;

        public Priority(Path path, int priority) {
            this.path = path;
            this.priority = priority;
        }

        public int compareTo(Priority other) {
            int priorityDiff = this.priority - other.priority;
            if (priorityDiff != 0) {
                return priorityDiff;
            }
            Iterator<State> iter1 = this.path.getStates().iterator();
            Iterator<State> iter2 = other.path.getStates().iterator();
            while (iter1.hasNext() && iter2.hasNext()) {
                State state1 = iter1.next();
                State state2 = iter2.next();
                int compare = state1.toString().compareTo(state2.toString());
                if (compare != 0) {
                    return compare;
                }
            }
            return (this.path.getStates().size() - other.path.getStates().size());
        }

    }

    private PriorityQueue<Priority> pq;

    public PathPriorityQueue() {
        this.pq = new PriorityQueue<Priority>();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() {
        return this.pq.size();
    }

    public void add(Path path, int priority) {
        this.pq.add(new Priority(path, priority));
    }

    public Path poll() {
        if (this.pq.isEmpty()) {
            return null;
        }
        return pq.poll().path;
    }

    public Path peek() {
        if (this.pq.isEmpty()) {
            return null;
        }
        return pq.peek().path;
    }

}