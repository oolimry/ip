package ducky;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {

    private LocalDate from;
    private LocalDate to;

    /**
     * @param description Description of Event
     * @param from Starting date of event
     * @param to Ending date of event
     */
    public EventTask(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * @return the event printed in format for user to see
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + from.format(DateTimeFormatter.ofPattern(Constants.OUTPUT_DATE_FORMAT)) +
                " to: " + to.format(DateTimeFormatter.ofPattern(Constants.OUTPUT_DATE_FORMAT)) + ")";
    }

    /**
     * @return the format for saving, which is similar to the reading format
     */
    @Override
    public String toSaveFormat() {
        String res = "event " + description +
                " /from " + from.format(DateTimeFormatter.ofPattern(Constants.SAVE_DATE_FORMAT)) +
                " /to " + to.format(DateTimeFormatter.ofPattern(Constants.SAVE_DATE_FORMAT));
        if (this.isDone) {
            res += " /marked 1";
        }
        return res;
    }
}