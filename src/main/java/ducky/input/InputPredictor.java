package ducky.input;

import java.util.ArrayList;

public class InputPredictor {

    ArrayList<InputPattern> inputPatterns = new ArrayList<>();

    public InputPredictor() {
        inputPatterns.add(new InputPattern("list",
                new ConstantToken("list")
        ));

        inputPatterns.add(new InputPattern("bye",
                new ConstantToken("bye")
        ));

        inputPatterns.add(new InputPattern("todo",
                new ConstantToken("todo"),
                new StringToken("<todo_name>")
        ));
    }

    public void getNextPossibleSegments(String input) {
        for (InputPattern inputPattern : inputPatterns) {
            System.out.println(inputPattern.label);
            System.out.println(inputPattern.matchesPatternCompletely(input));
            
            if (inputPattern.matchesPatternCompletely(input)) {
                System.out.println(inputPattern.label);
            }
        }

    }
}
