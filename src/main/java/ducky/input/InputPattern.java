package ducky.input;

import java.util.ArrayList;
import java.util.List;

/**
 * A series of tokens that represent a valid command structure
 */
public class InputPattern extends ArrayList<Token> {
    final String label;
    public static final String NO_MATCHING_PREDICTION = "NO_MATCHING_PREDICTION";
    public static final String MATCHES_COMPLETELY = "MATCHES_COMPLETELY";

    InputPattern(String _label, Token... tokens) {
        addAll(List.of(tokens));
        this.label = _label;
    }

    /**
     * @param input a string input
     * @return
     *         1. MATCHES_COMPLETELY if input matches the input pattern completely
     *         2. NO_MATCIHNG_PREDICTION if it is impossible for the input to match the pattern
     *         even after continuing the command
     *         3. Otherwise, the recommended prediction for what to enter next
     *         which is the next token's getPreview()
     */
    String getPrediction(String input) {
        ArrayList<String> segments = new ArrayList<String>();
        segments.addAll(List.of(input.split(" ")));

        if (!input.isEmpty() && input.charAt(input.length() - 1) == ' ') {
            segments.add("");
        }

        if (segments.size() > this.size()) {
            return NO_MATCHING_PREDICTION;
        }

        // check everything but the last token
        // as the last token may not be done typing
        for (int i = 0; i < segments.size() - 1; i++) {
            String segment = segments.get(i);
            Token token = this.get(i);

            if (!token.matches(segment)) {
                return NO_MATCHING_PREDICTION;
            }
        }

        String finalSegment = segments.get(segments.size() - 1);
        Token finalToken = this.get(segments.size() - 1);

        if (!finalToken.prefixMatches(finalSegment)) {
            return NO_MATCHING_PREDICTION;
        }

        if (finalToken.matches(finalSegment) && segments.size() == this.size()) {
            return MATCHES_COMPLETELY;
        }

        else {
            return finalToken.getPreview();
        }
    }
}
