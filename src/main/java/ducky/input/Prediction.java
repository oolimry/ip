package ducky.input;

/**
 * A class to describe the result of trying to predict the next input string
 * According to an UnputPattern and an input String
 *
 * NO_MATCIHNG_PREDICTION refers to the input not matching the pattern at all
 * MATCHES_COMPLETELY refers to the input being complete and matches the pattern completely
 * if
 */
public class Prediction {
    public static final String NO_MATCHING_PREDICTION = "No Known Command";
    public static final String MATCHES_COMPLETELY = "Command is Complete";

    private boolean matchesCompletely;
    private boolean doesNotMatch;
    private String prediction;
    private int spacePaddingLength;

    private Prediction (boolean _matchesCompletely, boolean _doesNotMatch,
                       String _prediction, int _spacePaddingLength) {
        this.matchesCompletely = _matchesCompletely;
        this.doesNotMatch = _doesNotMatch;
        this.prediction = _prediction;
        this.spacePaddingLength = _spacePaddingLength;
    }

    public static Prediction newMatchCompletelyPrediction (int _spacePaddingLength) {
        return new Prediction(true, false, MATCHES_COMPLETELY, _spacePaddingLength);
    }

    public static Prediction newDoesNotMatchPrediction (int _spacePaddingLength) {
        return new Prediction(false, true, NO_MATCHING_PREDICTION, _spacePaddingLength);
    }

    public static Prediction newPrediction (String _prediction, int _spacePaddingLength) {
        return new Prediction(false, false, _prediction, _spacePaddingLength);
    }

    public String getFormattedPrediction() {
        return " ".repeat(spacePaddingLength) + prediction;
    }

    public String getPredicition() {
        return prediction;
    }

    public boolean isMatchesCompletely() {
        return matchesCompletely;
    }

    public boolean isDoesNotMatch() {
        return doesNotMatch;
    }
}
