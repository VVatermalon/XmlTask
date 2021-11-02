package by.skarulskaya.cards;

import by.skarulskaya.cards.builder.SimpleCardHandler;
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
            SimpleCardHandler handler = new SimpleCardHandler();
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(SRC);
            reader.parse(resource.toString());
        }
        catch(SAXException | IOException e) {
            logger.error(e);
        }
    }
}
