package by.skarulskaya.cards.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class CardErrorHandler implements ErrorHandler {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void warning(SAXParseException e) throws SAXException {
        logger.warn(getErrorPosition(e) + ": " + e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        logger.error(getErrorPosition(e) + ": " + e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        logger.fatal(getErrorPosition(e) + ": " + e.getMessage());
    }

    private String getErrorPosition(SAXParseException e) {
        return "line " + e.getLineNumber() + " : column " + e.getColumnNumber();
    }
}
