package eu.modernmt.processing.string2;

import eu.modernmt.model.Tag;
import eu.modernmt.model.Token;
import eu.modernmt.model.Word;

/**
 * Created by andrea on 22/02/17.
 */
public interface TokenFactory {

    TokenFactory WORD_FACTORY = new TokenFactory() {


        @Override
        public Token build(String text, String placeholder, String rightSpace, boolean hasRightSpace) {

            // TODO: must be implemented
            return new Token(text, placeholder, rightSpace);
        }


        @Override
        public String toString() {
            return "Word Factory";
        }
    };


    TokenFactory TAG_FACTORY = new TokenFactory() {
        @Override
        public Token build(String text, String placeholder, String rightSpace, boolean hasRightSpace) {

            // TODO: must be implemented
            return new Token(text, placeholder, rightSpace);
        }

        @Override
        public String toString() {
            return "Tag Factory";
        }
    };

    Token build(String text, String placeholder, String rightSpace, boolean hasRightSpace);

}
