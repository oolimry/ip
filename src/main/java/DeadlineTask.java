public class DeadlineTask extends Task {

    private String deadline;

    public DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }

    @Override
    public String toSaveFormat() {
        String res = "deadline " + description + " /by " + deadline;
        if (this.isDone) {
            res += " /marked 1";
        }
        return res;
    }
}