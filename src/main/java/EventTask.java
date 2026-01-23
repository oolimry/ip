public class EventTask extends Task {

    private String from;
    private String to;

    public EventTask(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + 
            " (from: " + this.from + " to: " + this.to + ")";
    }

    @Override
    public String toSaveFormat() {
        String res = "event " + description + " /from " + from + " /to " + to;
        if (this.isDone) {
            res += " /marked 1";
        }
        return res;
    }
}