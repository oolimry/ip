package ducky.input;

import java.util.function.Supplier;

/**
 * A Token that takes in any integer
 * The range of valid integers are given by the Suppliers minValueSupplier and maxValueSupplier
 */
public class IntegerToken extends Token {

    private final Supplier<Integer> minValueSupplier;
    private final Supplier<Integer> maxValueSupplier;


    /**
     * @param _minValueSupplier a supplier that generates the minimum possible integer in this field
     * @param _maxValueSupplier a supplier that generates the maximum possible integer in this field
     */
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
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean prefixMatches(String segment) {
        if (segment.isEmpty()) {
            return true;
        }

        try {
            int value = Integer.parseInt(segment);

            if (value > maxValueSupplier.get()) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
