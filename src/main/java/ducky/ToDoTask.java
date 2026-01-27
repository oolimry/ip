package ducky;

public class ToDoTask extends Task {

    /**
     * @param description the description of the todo
     */
    public ToDoTask(String description) {
        super(description);
    }

    /**
     * @return the to_do printed in format for user to see
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * @return the format for saving, which is similar to the reading format
     */
    @Override
    public String toSaveFormat() {
        String res = "todo " + description;
        if (this.isDone) {
            res += " /marked 1";
        }
        return res;
    }
}