public class Action {

    private String description;
    private int cost;
    private State nextState;

    public Action(String description, int cost, State nextState) {
        this.description = description;
        this.cost = cost;
        this.nextState = nextState;
    }

    public int getCost() {
        return this.cost;
    }

    public State getNextState() {
        return this.nextState;
    }

    public String toString() {
        return this.description;
    }

}