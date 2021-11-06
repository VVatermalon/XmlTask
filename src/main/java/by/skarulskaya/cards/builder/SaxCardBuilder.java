package by.skarulskaya.cards.builder;

import by.skarulskaya.cards.entity.Card;
import by.skarulskaya.cards.exception.CardException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class SaxCardBuilder extends AbstractCardBuilder {
    private CardHandler handler;
    private XMLReader reader;

    public SaxCardBuilder() throws CardException {
        handler = new CardHandler();

        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
            reader.setErrorHandler(new CardErrorHandler());
        } catch (SAXException e) {
            throw new CardException("Error during configuration of SAX parser", e);
        }
    }

    public SaxCardBuilder(Set<Card> cards) throws CardException {
        super(cards);
        handler = new CardHandler();

        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
            reader.setErrorHandler(new CardErrorHandler());
        } catch (SAXException e) {
            throw new CardException("Error during configuration of SAX parser", e);
        }
    }

    @Override
    public void buildCards(String xmlSrc) throws CardException {
        try {
            reader.parse(xmlSrc);
        } catch (IOException e) {
            throw new CardException("Unable to open XML file " + xmlSrc, e);
        } catch (SAXException e) {
            throw new CardException("Error during parsing XML file " + xmlSrc, e);
        }

        cards = handler.getCards();
    }
}
