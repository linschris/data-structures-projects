import java.util.List;

public abstract class State {

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            return this.toString().equals(obj.toString());
        }
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public abstract List<Action> getActions();

    public abstract int heuristicTo(State goal);

    public abstract String toString();

    public abstract void print();
}