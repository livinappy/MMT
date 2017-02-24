package eu.modernmt.processing.string2;

/**
 *  * Created by andrea on 22/02/17.
 *
 * A Transformation (temporary name)
 * represents any kind of change that editors can request
 * while scanning their string.
 *
 * A Transformation object has fields for each piece of information
 * that a single string processing request may need, such as:
 * - start index of the text under processing on the original string;
 * - end index of the text under processing on the original string;
 * - a string with the text under processing itself;
 * - a string with what must be used to replace text (if replacement is needed);
 * - a reference to a suitable TokenFactory (Word, Tag, etc).
 *
 * Of course if the transformation does not involve replacement,
 * the replacement field is set to null.
 * On the other hand, simple replacements do not lead to token generation,
 * therefore their tokenFactory field is set to null.
 *
 * The Transformation objects are kept in separate ordered lists
 * depending on the Editor that generated them.
 * When the Editor executes commit(), its transformations
 * are sent to the SentenceBuilder;
 * when the SentengeBuilder executes build(), they are employed
 * to actually generate Tokens, that are employed to form the final Sentence object.
 */
public class Transformation {
    private int start;      // start index on the original string
    private int end;        //end index on the original string
    private String text;        //string to edit
    private String replacement; //string to use to replace text
    private TokenFactory tokenFactory;

    public Transformation(int start,
                          int end,
                          String text,
                          String replacement,
                          TokenFactory tokenFactory) {
        this.start = start;
        this.end = end;
        this.text = text;
        this.tokenFactory = tokenFactory;
        this.replacement = replacement;
    }


    /*getters and setters*/

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public TokenFactory getTokenFactory() {
        return tokenFactory;
    }

    public void setTokenFactory(TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }


    /*toString - mainly used for debugging*/
    public String toString() {
        String description = "Start Index on original string: " + this.start + "\n";
        description +=       "  End Index on original string: " + this.end + "\n";
        description +=       "                         Text: '" + this.text + "'\n";
        description +=       "                  Replacement: '" + this.replacement + "'\n";
        description +=       "                 Token Factory? " + this.tokenFactory;

        return description;
    }
}