package ducky;

public class ToDoTask extends Task {

    public ToDoTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toSaveFormat() {
        String res = "todo " + description;
        if (this.isDone) {
            res += " /marked 1";
        }
        return res;
    }
}