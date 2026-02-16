package ducky.input;

import java.util.ArrayList;
import java.util.List;

/**
 * A series of tokens that represent a valid command structure
 */
public class InputPattern extends ArrayList<Token> {
    final String label;


    public InputPattern(String _label, Token... tokens) {
        addAll(List.of(tokens));
        this.label = _label;
    }

    /**
     * Gets the next prediction of a string based on the input
     *
     * @param input a string input
     * @return
     *         1. MATCHES_COMPLETELY if input matches the input pattern completely
     *         2. NO_MATCIHNG_PREDICTION if it is impossible for the input to match the pattern
     *         even after continuing the command
     *         3. Otherwise, the recommended prediction for what to enter next
     *         which is the next token's getPreview()
     */
    Prediction getPrediction(String input) {
        String consecutiveWhitespaceStripped = input.replaceAll("\\s+", " ");

        ArrayList<String> combinedSegments = getCombinedSegments(consecutiveWhitespaceStripped);

        if (combinedSegments.size() > this.size()) {
            return Prediction.newDoesNotMatchPrediction(input.length());
        }

        int cummulativeLengthOfTokens = 0;
        // check everything but the last token
        // as the last token may not be done typing
        for (int i = 0; i < combinedSegments.size() - 1; i++) {
            String segment = combinedSegments.get(i);
            Token token = this.get(i);

            if (!token.matches(segment)) {
                return Prediction.newDoesNotMatchPrediction(cummulativeLengthOfTokens);
            }

            cummulativeLengthOfTokens += segment.length() + 1;
        }

        String finalSegment = combinedSegments.get(combinedSegments.size() - 1);
        Token finalToken = this.get(combinedSegments.size() - 1);

        if (!finalToken.prefixMatches(finalSegment)) {
            return Prediction.newDoesNotMatchPrediction(cummulativeLengthOfTokens);
        }

        if (finalToken.matches(finalSegment) && combinedSegments.size() == this.size()) {
            return Prediction.newMatchCompletelyPrediction(cummulativeLengthOfTokens);
        }

        else {
            String previewString = finalToken.getPreview();

            if (finalToken.allowSpaces() && combinedSegments.size() < this.size()) {
                Token afterFinalToken = this.get(combinedSegments.size());
                previewString += " " + afterFinalToken.getPreview();
            }

            return Prediction.newPrediction(previewString, cummulativeLengthOfTokens);
        }
    }

    /**
     * Splits the input text into space seperated segments
     * Based on the input pattern, detect where to join segments together
     *
     * @param input the string as input
     * @return the list of segments that matches the token pattern
     */
    private ArrayList<String> getCombinedSegments(String input) {
        ArrayList<String> rawSegments = new ArrayList<String>();
        rawSegments.addAll(List.of(input.split(" ")));
        if (!input.isEmpty() && input.charAt(input.length() - 1) == ' ') {
            rawSegments.add("");
        }

        ArrayList<String> combinedSegments = new ArrayList<>();

        int segmentPointer = 0;
        for (int i = 0; i < this.size(); i++) {
            if (segmentPointer == rawSegments.size()) {
                break;
            }

            Token token = this.get(i);
            if (token.allowSpaces()) {
                ArrayList<String> segmentsToJoin = new ArrayList<>();

                if (i != this.size() - 1) {
                    // case 1: there is a next token
                    // find the position and add everything in between
                    Token nextToken = this.get(i+1);

                    while (segmentPointer < rawSegments.size()) {
                        String nextSegment = rawSegments.get(segmentPointer);

                        if (nextToken.matches(nextSegment)) {
                            break;
                        }

                        segmentsToJoin.add(nextSegment);
                        segmentPointer += 1;
                    }
                } else {
                    // case 2: there is no next token
                    // just keep appending all the way to the end

                    while (segmentPointer < rawSegments.size()) {
                        String nextSegment = rawSegments.get(segmentPointer);

                        segmentsToJoin.add(nextSegment);
                        segmentPointer += 1;
                    }
                }

                String segmentToAdd = String.join(" ", segmentsToJoin);
                combinedSegments.add(segmentToAdd);
            } else {
                combinedSegments.add(rawSegments.get(segmentPointer));
                segmentPointer += 1;
            }
        }

        while (segmentPointer < rawSegments.size()) {
            combinedSegments.add(rawSegments.get(segmentPointer));
            segmentPointer += 1;
        }

        return combinedSegments;
    }
}
