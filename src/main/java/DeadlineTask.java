import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {

    private LocalDate deadline;

    public DeadlineTask(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() +
                " (by: " + deadline.format(DateTimeFormatter.ofPattern(Constants.OUTPUT_DATE_FORMAT)) + ")";
    }

    @Override
    public String toSaveFormat() {
        String res = "deadline " + description +
                " /by " + deadline.format(DateTimeFormatter.ofPattern(Constants.SAVE_DATE_FORMAT));
        if (this.isDone) {
            res += " /marked 1";
        }
        return res;
    }
}