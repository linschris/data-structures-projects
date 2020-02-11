import java.util.List;
import java.util.ArrayList;

public class MazeState extends State {

    private int width;
    private int height;
    private int row;
    private int col;
    private boolean[][] spaces;

    public MazeState(String mapString) {
        // spaces, | for newline, X for wall, O for location
        String[] rows = mapString.split("\\|");
        this.width = 0;
        for (String line : rows) {
            if (line.length() > width) {
                this.width = line.length();
            }
        }
        this.height = rows.length;
        this.spaces = new boolean[height][width];
        for (int r = 0; r < this.height; r++) {
            if (rows[r].length() != this.width) {
                throw new IllegalArgumentException("board is not a rectangle");
            }
            for (int c = 0; c < this.width; c++) {
                char mapChar = rows[r].charAt(c);
                if (mapChar == '*') {
                    this.row = r;
                    this.col = c;
                    this.spaces[r][c] = true;
                } else {
                    this.spaces[r][c] = (mapChar == ' ');
                }
            }
        }
    }

    public List<Action> getActions() {
        List<Action> actions = new ArrayList<Action>();
        if (this.row > 0 && this.spaces[row-1][col]) {
            actions.add(new Action(
                "up", 1,
                new MazeState(this.toString(this.row - 1, this.col))
            ));
        }
        if (this.row < this.height - 1 && this.spaces[row+1][col]) {
            actions.add(new Action(
                "down", 1,
                new MazeState(this.toString(this.row + 1, this.col))
            ));
        }
        if (this.col > 0 && this.spaces[row][col-1]) {
            actions.add(new Action(
                "left", 1,
                new MazeState(this.toString(this.row, this.col - 1))
            ));
        }
        if (this.col < this.width - 1 && this.spaces[row][col+1]) {
            actions.add(new Action(
                "right", 1,
                new MazeState(this.toString(this.row, this.col + 1))
            ));
        }
        return actions;
    }

    public int heuristicTo(State state) {
        if (state == null || this.getClass() != state.getClass()) {
            throw new IllegalArgumentException("tried to get heuristic to state from another puzzle");
        }
        MazeState goal = (MazeState) state;
        return (
            Math.abs(this.row - goal.row)
            + Math.abs(this.col - goal.col)
        );
    }

    public String toString() {
        return this.toString(this.row, this.col);
    }

    private String toString(int rowPos, int colPos) {
        String result = "";
        for (int r = 0; r < this.height; r++) {
            result += "|";
            for (int c = 0; c < this.width; c++) {
                if (rowPos == r && colPos == c) {
                    result += "*";
                } else if (this.spaces[r][c]) {
                    result += " ";
                } else {
                    result += "#";
                }
            }
        }
        return result.substring(1);
    }

    public void print() {
        System.out.print("+");
        for (int i = 0; i < this.width; i++) {
            System.out.print("-");
        }
        System.out.println("+");
        for (String line : this.toString().split("\\|")) {
            System.out.println("|" + line + "|");
        }
        System.out.print("+");
        for (int i = 0; i < this.width; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }

}