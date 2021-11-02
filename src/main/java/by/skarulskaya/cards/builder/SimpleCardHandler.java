package by.skarulskaya.cards.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SimpleCardHandler extends DefaultHandler {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void startDocument() {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        String s = localName;
        for (int i = 0; i < attrs.getLength(); i++) {
            s += " " + attrs.getLocalName(i) + "=" + attrs.getValue(i);
        }
        logger.info(s.trim());
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        logger.info(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        logger.info(localName);
    }

    @Override
    public void endDocument() {

    }
}
