package ducky.input;

import java.util.function.Supplier;

public class IntegerToken extends Token {

    private Supplier<Integer> minValueSupplier;
    private Supplier<Integer> maxValueSupplier;

    public IntegerToken(Supplier<Integer> _minValueSupplier, Supplier<Integer> _maxValueSupplier) {
        this.minValueSupplier = _minValueSupplier;
        this.maxValueSupplier = _maxValueSupplier;
    }

    @Override
    public String getPreview() {
        return "[" + minValueSupplier.get() + "..." + maxValueSupplier.get() + "]";
    }

    @Override
    public boolean matches(String segment) {
        try {
            int value = Integer.parseInt(segment);

            if (value < minValueSupplier.get()) {
                return false;
            }
            if (value > maxValueSupplier.get()) {
                return false;
            }

            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
