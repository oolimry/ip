package ducky.input;

import ducky.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeToken extends Token {

    public DateTimeToken(String _constant) {
    }

    @Override
    public String getPreview() {
        return Constants.SAVE_DATE_FORMAT;
    }

    @Override
    public boolean matches(String segment) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.SAVE_DATE_FORMAT);
            LocalDate.parse(segment, formatter);
            return true;
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }
}
