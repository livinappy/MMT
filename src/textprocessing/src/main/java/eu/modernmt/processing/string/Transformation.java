package eu.modernmt.processing.string;

/**
 * * Created by andrea on 22/02/17.
 * <p>
 * A Transformation is any kind of activity that an Editor can request
 * while scanning the StringBuilder current version of the string under analysis.
 * <p>
 * A Transformation has fields for each piece of information
 * that a single processing activity may need, such as:
 * - start index of the text to edit on the original string;
 * - end index of the text to edit on the original string;
 * - a string with the text to edit itself;
 * - a string with the new text that must replace replace the text to edit (optional);
 * - a reference to the TokenFactory that suits the transformation (Word, Tag, etc).
 * <p>
 * If a transformation does not involve replacement, the replacement field is null.
 * In general, word settings do not involve replacement,
 * <p>
 * If a transformation does not lead to token generation, its tokenFactory field is null.
 * In general, pure replacements do not lead to token generation.
 * <p>
 * IN the tokenization process, each Editor creates its Transformations
 * and stores them in a list of its own.
 * When the Editor executes commit(), its transformation are sent to the SentenceBuilder.
 * When the SentenceBuilder builds the Sentence as a result of the tokenization process,
 * Transformation are used to generate the Sentence tokens.
 */
public class Transformation {
    public final int START;      // start index on the original string
    public final int END;        //end index on the original string
    public final String TEXT;        //string to edit
    public final String REPLACEMENT; //string to use to replace text
    public final TokenFactory TOKEN_FACTORY;

    /**
     * Constructor that generates a Transformation object
     * based on the way it must affect the string under analysis
     *
     * @param start        The start position of the transformation on the original version of the string
     * @param end          The end position of the transformation on the original version of the string
     * @param text         The text under analysis, as found in the current version of the string
     * @param replacement  A string that must replace the text under analysis
     * @param tokenFactory The object that should be used to create a token from this transformation
     */
    public Transformation(int start,
                          int end,
                          String text,
                          String replacement,
                          TokenFactory tokenFactory) {
        this.START = start;
        this.END = end;
        this.TEXT = text;
        this.REPLACEMENT = replacement;
        this.TOKEN_FACTORY = tokenFactory;
    }

    /**
     * Creates and returns a textual description of this transformation.
     * Mainly used for debugging purposes.
     *
     * @return the String description of this transformation
     */
    public String toString() {
        String description = "Start Index on original string: " + this.START + "\n";
        description += "  End Index on original string: " + this.END + "\n";
        description += "                         Text: '" + this.TEXT + "'\n";
        description += "                  Replacement: '" + this.REPLACEMENT + "'\n";
        description += "                 Token Factory? " + this.TOKEN_FACTORY;
        return description;
    }
}