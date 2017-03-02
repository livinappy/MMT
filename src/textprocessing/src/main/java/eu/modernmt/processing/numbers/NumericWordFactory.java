package eu.modernmt.processing.numbers;

import eu.modernmt.model.Word;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by davide on 04/04/16.
 * Updated by andrearossi on 01/03/2017
 */
//public class NumericWordFactory implements SentenceBuilder.WordFactory {

public class NumericWordFactory {

    private static final Pattern PATTERN = Pattern.compile(".*[0-9].*");

    public boolean match(String text, String placeholder) {
        return PATTERN.matcher(text).find();
    }

    public Word build(String text, String placeholder, String rightSpace) {
        char[] chars = placeholder.toCharArray();

        replaceDigits(chars, 0);

        return new Word(text, new String(chars), rightSpace);
    }

    public void setMetadata(Map<String, Object> metadata) {
        // Nothing to do
    }

    private static void replaceDigits(char[] chars, int digit) {
        char d = (char) ('0' + (digit % 10));

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c >= '0' && c <= '9')
                chars[i] = d;
        }
    }
}
