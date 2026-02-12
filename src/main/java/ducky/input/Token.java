package ducky.input;

public abstract class Token {

    abstract public String getPreview();

    abstract public boolean matches(String segment);

    public boolean prefixMatches(String segment) {
        return true;
    }
}
