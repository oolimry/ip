package ducky.input;

/**
 * A Token that takes in any non-empty String as an input
 */
public class StringToken extends Token {
    public String tokenPreview;

    /**
     * @param _tokenPreview the description of what this string should be
     *                      e.g. <task_name>
     */
    public StringToken(String _tokenPreview) {
        this.tokenPreview =_tokenPreview;
    }

    @Override
    public String getPreview() {
        return tokenPreview;
    }

    @Override
    public boolean matches(String segment) {
        if (segment.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean allowSpaces() {
        return true;
    }
}
