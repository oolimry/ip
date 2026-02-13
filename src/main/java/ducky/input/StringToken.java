package ducky.input;

public class StringToken extends Token {
    public String tokenPreview;

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


}
