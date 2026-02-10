package ducky.input;

public class ConstantToken extends Token {
    private final String constant;

    public ConstantToken(String _constant) {
        this.constant = _constant;
    }

    @Override
    public String getPreview() {
        return constant;
    }

    @Override
    public boolean matches(String segment) {
        return segment.equals(constant);
    }
}
