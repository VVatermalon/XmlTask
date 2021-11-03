package by.skarulskaya.cards;

import by.skarulskaya.cards.builder.CardHandler;
import by.skarulskaya.cards.builder.SaxCardBuilder;
import by.skarulskaya.cards.exception.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.net.URL;

public class main {
    static final Logger logger = LogManager.getLogger();
    static final String SRC = "card.xml";
    public static void main(String[] args) {
        try {
            SaxCardBuilder builder = new SaxCardBuilder();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(SRC);
            builder.buildCards(resource.toString());
            builder.getCards().forEach(logger::info);
        }
        catch(CardException e) {
            logger.error(e);
        }
    }
}
