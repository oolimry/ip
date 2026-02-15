package ducky.input;

/**
 * An abstract class for representing a single token
 * A command consists of several tokens following an InputPattern
 */
public abstract class Token {

    /**
     * @return the string that should appear as a suggestion
     */
    abstract public String getPreview();

    /**
     * @param segment a string entered for a token
     * @return whether the segment is a valid string matching the token's requirements
     */
    abstract public boolean matches(String segment);


    /**
     * @param segment a string entered for a token
     * @return if the segment is definitely not a prefix of a valid input of the token, returns false.
     *         else it returns true
     */
    public boolean prefixMatches(String segment) {
        return true;
    }
}
