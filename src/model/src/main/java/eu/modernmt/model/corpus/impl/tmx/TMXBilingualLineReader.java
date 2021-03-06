package eu.modernmt.model.corpus.impl.tmx;

import eu.modernmt.io.FileProxy;
import eu.modernmt.model.corpus.BilingualCorpus;
import eu.modernmt.xml.XMLUtils;
import org.apache.commons.io.IOUtils;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by davide on 14/03/16.
 */
class TMXBilingualLineReader implements BilingualCorpus.BilingualLineReader {

    private final TMXPairReader tmxPairReader = new TMXPairReader();

    private final FileProxy tmx;
    private final InputStream stream;
    private final XMLEventReader reader;
    private final String sourceLanguage;
    private final String targetLanguage;

    TMXBilingualLineReader(FileProxy tmx, Locale sourceLanguage, Locale targetLanguage) throws IOException {
        this.tmx = tmx;
        this.sourceLanguage = sourceLanguage.getLanguage();
        this.targetLanguage = targetLanguage.getLanguage();

        InputStream stream = null;
        XMLEventReader reader = null;

        try {
            stream = tmx.getInputStream();
            reader = XMLUtils.createEventReader(stream);
        } catch (XMLStreamException e) {
            throw new IOException("Error while creating XMLStreamReader for TMX " + tmx, e);
        } finally {
            if (reader == null)
                IOUtils.closeQuietly(stream);
        }

        this.stream = stream;
        this.reader = reader;
    }

    @Override
    public BilingualCorpus.StringPair read() throws IOException {
        try {
            return tmxPairReader.read(reader, sourceLanguage, targetLanguage);
        } catch (XMLStreamException e) {
            throw new IOException("Invalid TMX " + tmx, e);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            reader.close();
        } catch (XMLStreamException e) {
            throw new IOException("Error while closing XMLStreamReader", e);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

}
