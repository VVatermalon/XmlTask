package by.skarulskaya.cards;

import by.skarulskaya.cards.builder.*;
import by.skarulskaya.cards.exception.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class main {
    static final Logger logger = LogManager.getLogger();
    static final String SRC = "card.xml";
    public static void main(String[] args) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(SRC);
            AbstractCardBuilder builder = CardBuilderFactory.getBuilder("STAX");
            builder.buildCards(Paths.get(resource.toURI()).toString());
            builder.getCards().forEach(logger::info);
        } catch (CardException | URISyntaxException e) {
            logger.error(e);
        }
    }
}
