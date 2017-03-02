package eu.modernmt.processing.xmessage;

import eu.modernmt.processing.LanguageNotSupportedException;
import eu.modernmt.processing.TextProcessor;
import eu.modernmt.processing.string.SentenceBuilder;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by davide on 08/04/16.
 * Updated by andrearossi on 01/03/2017.
 * <p>
 * An XMessageTokenizer has the responsibility
 * to find XMessages in strings,
 * to find the words inside XMessages
 * and to actively request to build Tokens from them.
 */
public class XMessageTokenizer extends TextProcessor<SentenceBuilder, SentenceBuilder> {

    /**
     * This constructor builds an XMessageTokenizer object
     * based on the initial language and the target language of the translation process
     *
     * @param sourceLanguage the initial language of the string that must be translated
     * @param targetLanguage the language that string must be translated to
     * @throws LanguageNotSupportedException if a not supported language is involved
     */
    public XMessageTokenizer(Locale sourceLanguage, Locale targetLanguage) throws LanguageNotSupportedException {
        super(sourceLanguage, targetLanguage);
    }

    /**
     * Method that, given a SentenceBuilder with the string to process,
     * extracts the string,
     * uses it to create a Matcher object for the XMessage in the text,
     * extract the words inside the xmessage,
     * and requests the creation of a Word token for each of them.
     *
     * @param builder  a SentenceBuilder that holds the input String
     *                 and can generate Editors to process it
     * @param metadata additional information on the current pipe
     *                 (not used in this specific operation)
     * @return the SentenceBuilder received as a parameter;
     * its internal state has been updated by the execution of the call() method
     */
    @Override
    public SentenceBuilder call(SentenceBuilder builder, Map<String, Object> metadata) {
        SentenceBuilder.Editor editor = builder.edit();

        /*Build a matcher for the xmessages in the string*/
        Matcher m = XFormat.PLACEHOLDER_PATTERN.matcher(builder.toString());

        /*each match corresponds to a word found inside the XMessage*/
        while (m.find()) {

            int start = m.start();
            int end = m.end();

            editor.setWord(start, end - start, null);
        }

        editor.commit();
        return builder;
    }
}