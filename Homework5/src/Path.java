import java.util.List;
import java.util.LinkedList;

public class Path {

    private LinkedList<State> states;
    private LinkedList<Action> actions;

    public Path(State state) {
        this.states = new LinkedList<State>();
        this.actions = new LinkedList<Action>();
        states.addLast(state);
    }

    public Path(Path path, Action nextAction) {
        this.states = new LinkedList<State>();
        this.actions = new LinkedList<Action>();
        for (State state : path.getStates()) {
            this.states.add(state);
        }
        this.states.add(nextAction.getNextState());
        for (Action action : path.getActions()) {
            this.actions.add(action);
        }
        this.actions.add(nextAction);
    }

    public List<State> getStates() {
        List<State> result = new LinkedList<State>();
        for (State state : this.states) {
            result.add(state);
        }
        return result;
    }

    public State getLastState() {
        return this.states.getLast();
    }

    public List<Action> getActions() {
        List<Action> result = new LinkedList<Action>();
        for (Action action : this.actions) {
            result.add(action);
        }
        return result;
    }

    public int getCost() {
        int total = 0;
        for (Action action : this.actions) {
            total += action.getCost();
        }
        return total;
    }

}