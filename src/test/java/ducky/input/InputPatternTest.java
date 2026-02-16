package ducky.input;

import ducky.input.ConstantToken;
import ducky.input.DateTimeToken;
import ducky.input.InputPattern;
import ducky.input.StringToken;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class InputPatternTest {
    @Test
    public void InputPattern_deadline() {
        InputPattern deadlinePattern = new InputPattern("deadline",
                new ConstantToken("deadline"),
                new StringToken("<description>"),
                new ConstantToken("/by"),
                new DateTimeToken()
        );

        assertEquals(Prediction.MATCHES_COMPLETELY,
                deadlinePattern.getPrediction("deadline abc /by 2026-01-31").getPredicition());

        // don't allow pure spaces as a task description
        assertEquals(Prediction.NO_MATCHING_PREDICTION,
                deadlinePattern.getPrediction("deadline      /by 2026-01-31").getPredicition());

        // don't allow empty string for task description
        assertEquals(Prediction.NO_MATCHING_PREDICTION,
                deadlinePattern.getPrediction("deadline /by 2026-01-31").getPredicition());

        // show the correct next thing
        assertEquals("deadline",
                deadlinePattern.getPrediction("d").getPredicition());

        // show the correct next thing
        assertEquals("deadline",
                deadlinePattern.getPrediction("deadline").getPredicition());

        // show the correct next thing
        assertEquals("<description> /by",
                deadlinePattern.getPrediction("deadline ").getPredicition());

        // show the correct next thing
        assertEquals("<description> /by",
                deadlinePattern.getPrediction("deadline     ").getPredicition());

        // show the correct next thing
        assertEquals("<description> /by",
                deadlinePattern.getPrediction("deadline desc").getPredicition());

        // show the correct next thing
        assertEquals("<description> /by",
                deadlinePattern.getPrediction("deadline desc ").getPredicition());

        assertEquals("<description> /by",
                deadlinePattern.getPrediction("deadline desc /").getPredicition());

        assertEquals("<description> /by",
                deadlinePattern.getPrediction("deadline desc e").getPredicition());

        assertEquals("/by",
                deadlinePattern.getPrediction("deadline desc /by").getPredicition());

        assertEquals("yyyy-MM-dd",
                deadlinePattern.getPrediction("deadline desc /by ").getPredicition());
    }

    @Test
    public void InputPattern_todo() {
        InputPattern todoPattern = new InputPattern("todo",
                new ConstantToken("todo"),
                new StringToken("<description>")
        );

        assertEquals("todo",
                todoPattern.getPrediction("todo").getPredicition());

        assertEquals("<description>",
                todoPattern.getPrediction("todo ").getPredicition());

        assertEquals(Prediction.MATCHES_COMPLETELY,
                todoPattern.getPrediction("todo abc").getPredicition());
    }

    @Test
    public void InputPattern_mark() {
        Supplier<Integer> minValueSupplier;
        minValueSupplier = () -> {
            return 1;
        };

        Supplier<Integer> maxValueSupplier;
        maxValueSupplier = () -> {
            return 12;
        };

        InputPattern markPattern = new InputPattern("mark",
                new ConstantToken("mark"),
                new IntegerToken(minValueSupplier, maxValueSupplier)
        );

        assertEquals(Prediction.MATCHES_COMPLETELY,
                markPattern.getPrediction("mark 2").getPredicition());

        assertEquals("[1...12]",
                markPattern.getPrediction("mark ").getPredicition());

        assertEquals(Prediction.NO_MATCHING_PREDICTION,
                markPattern.getPrediction("mark abc cde").getPredicition());

        assertEquals(Prediction.NO_MATCHING_PREDICTION,
                markPattern.getPrediction("mark abc ").getPredicition());

        assertEquals(Prediction.NO_MATCHING_PREDICTION,
                markPattern.getPrediction("mark abc ").getPredicition());

        assertEquals(Prediction.NO_MATCHING_PREDICTION,
                markPattern.getPrediction("mark 13").getPredicition());
    }


}