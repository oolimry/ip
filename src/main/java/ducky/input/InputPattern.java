package ducky.input;

import java.util.ArrayList;
import java.util.List;

public class InputPattern extends ArrayList<Token> {
    final String label;

    InputPattern(String _label, Token... tokens) {
        addAll(List.of(tokens));
        this.label = _label;
    }

    boolean matchesPatternCompletely(String input) {
        ArrayList<String> segments = new ArrayList<String>();
        segments.addAll(List.of(input.split(" ")));

        if (!input.isEmpty() && input.charAt(input.length() - 1) == ' ') {
            segments.add("");
        }

        if (segments.size() != this.size()) {
            return false;
        }

        // check everything but the last token
        // as the last token may not be done typing
        for (int i = 0; i < segments.size(); i++){
            String segment = segments.get(i);
            Token token = this.get(i);

            if (!token.matches(segment)) {
                return false;
            }
        }

        return true;
    }

    boolean matchesPatternSoFar(String input) {
        ArrayList<String> segments = new ArrayList<String>();
        segments.addAll(List.of(input.split(" ")));

        if (input.charAt(input.length() - 1) == ' ') {
            segments.add("");
        }

        if (segments.size() > this.size()) {
            return false;
        }

        // check everything but the last token
        // as the last token may not be done typing
        for (int i = 0; i < segments.size() - 1; i++){
            String segment = segments.get(i);
            Token token = this.get(i);

            if (!token.matches(segment)) {
                return false;
            }
        }

        return true;
    }
}
