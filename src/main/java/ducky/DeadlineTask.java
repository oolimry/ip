package ducky;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {

    private LocalDate deadline;

    /**
     * Constructor for DeadlineTask
     *
     * @param description Description of Task
     * @param deadline Deadline of the task
     */
    public DeadlineTask(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }


    /**
     * @return the deadline printed in format for user to see
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() +
                " (by: " + deadline.format(DateTimeFormatter.ofPattern(Constants.OUTPUT_DATE_FORMAT)) + ")";
    }

    /**
     * @return the format for saving, which is similar to the reading format
     */
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