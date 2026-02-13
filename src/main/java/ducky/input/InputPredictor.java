package ducky.input;

import java.util.ArrayList;
import java.util.function.Supplier;

public class InputPredictor {
    ArrayList<InputPattern> inputPatterns = new ArrayList<>();

    public InputPredictor(Supplier<Integer> taskListSizeSupplier) {
        Supplier<Integer> minValueSupplier;
        minValueSupplier = () -> {
            return 1;
        };

        inputPatterns.add(new InputPattern("list",
                new ConstantToken("list")
        ));

        inputPatterns.add(new InputPattern("mark",
                new ConstantToken("mark"),
                new IntegerToken(minValueSupplier, taskListSizeSupplier)
        ));

        inputPatterns.add(new InputPattern("unmark",
                new ConstantToken("unmark"),
                new IntegerToken(minValueSupplier, taskListSizeSupplier)
        ));

        inputPatterns.add(new InputPattern("delete",
                new ConstantToken("delete"),
                new IntegerToken(minValueSupplier, taskListSizeSupplier)
        ));

        inputPatterns.add(new InputPattern("todo",
                new ConstantToken("todo"),
                new StringToken("<description>")
        ));

        inputPatterns.add(new InputPattern("deadline",
                new ConstantToken("deadline"),
                new StringToken("<description>"),
                new ConstantToken("/by"),
                new DateTimeToken()
        ));

        inputPatterns.add(new InputPattern("event",
                new ConstantToken("event"),
                new StringToken("<description>"),
                new ConstantToken("/from"),
                new DateTimeToken(),
                new ConstantToken("/to"),
                new DateTimeToken()
        ));

        inputPatterns.add(new InputPattern("find",
                new ConstantToken("find"),
                new StringToken("<description to find>")
        ));

        inputPatterns.add(new InputPattern("bye",
                new ConstantToken("bye")
        ));
    }

    public ArrayList<String> getNextPossibleSegments(String input) {
        ArrayList<String> possibleSegments = new ArrayList<String>();

        for (InputPattern inputPattern : inputPatterns) {
            String prediction = inputPattern.getPrediction(input);
            if(!prediction.equals(InputPattern.NO_MATCHING_PREDICTION)) {
                possibleSegments.add(prediction);
            }
        }

        return possibleSegments;
    }
}
