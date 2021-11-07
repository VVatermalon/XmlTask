package by.skarulskaya.cards.builder;

import by.skarulskaya.cards.entity.*;
import by.skarulskaya.cards.exception.CardException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class DOMCardBuilderTest {
    static String normalData = "cardNormal.xml";
    static String data1Attribute = "card1Attribute.xml";
    static String data2Attributes = "card2Attributes.xml";
    static String dataInvertedOrderAttribute = "cardInvertedOrderAttribute.xml";
    static String WRONG_PATH = "cardd.xml";

    @BeforeClass
    void setPaths() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(normalData);
        try {
            normalData = Paths.get(resource.toURI()).toString();
            resource = classLoader.getResource(data1Attribute);
            data1Attribute = Paths.get(resource.toURI()).toString();
            resource = classLoader.getResource(data2Attributes);
            data2Attributes = Paths.get(resource.toURI()).toString();
            resource = classLoader.getResource(dataInvertedOrderAttribute);
            dataInvertedOrderAttribute = Paths.get(resource.toURI()).toString();
        } catch (URISyntaxException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBuildCards() {
        try {
            DOMCardBuilder builder = new DOMCardBuilder();
            builder.buildCards(normalData);
            Set<Card> actual = builder.getCards();
            Set<Card> expected = new HashSet<>();
            Card card = new SentCard("i378", CardTheme.ANIMALS, CardType.GREETING, CardCountry.BELARUS,
                    Optional.of("Aversev"), false,
                    LocalDateTime.of(2021, 10, 10, 18, 0, 0),
                    Optional.of("XOXO"));
            expected.add(card);
            assertEquals(actual, expected);
        } catch (CardException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBuildCards1Attribute() {
        try {
            DOMCardBuilder builder = new DOMCardBuilder();
            builder.buildCards(data1Attribute);
            Set<Card> actual = builder.getCards();
            Set<Card> expected = new HashSet<>();
            Card card = new StoreCard("k37", CardTheme.CARS, CardType.GREETING, CardCountry.RUSSIA,
                    Optional.empty(), true, 3.33, Year.of(2015));
            expected.add(card);
            assertEquals(actual, expected);
        } catch (CardException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBuildCards2Attributes() {
        try {
            DOMCardBuilder builder = new DOMCardBuilder();
            builder.buildCards(data2Attributes);
            Set<Card> actual = builder.getCards();
            Set<Card> expected = new HashSet<>();
            Card card = new SentCard("k3", CardTheme.PEOPLE, CardType.ADVERTISING, CardCountry.RUSSIA,
                    Optional.empty(), false,
                    LocalDateTime.of(2021, 10, 8, 13, 0, 0),
                    Optional.of("Welcome to the concert!"));
            expected.add(card);
            assertEquals(actual, expected);
        } catch (CardException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBuildCardsInvertedOrderAttributes() {
        try {
            DOMCardBuilder builder = new DOMCardBuilder();
            builder.buildCards(dataInvertedOrderAttribute);
            Set<Card> actual = builder.getCards();
            Set<Card> expected = new HashSet<>();
            Card card = new HandmadeCard("c10", CardTheme.ANIMALS, CardType.GREETING, CardCountry.LITHUANIA,
                    Optional.of("Hey Helen"), false,
                    40, Duration.ofHours(10));
            expected.add(card);
            assertEquals(actual, expected);
        } catch (CardException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = CardException.class)
    public void testBuildCardsWrongPath() throws CardException {
        DOMCardBuilder builder = new DOMCardBuilder();
        builder.buildCards(WRONG_PATH);
    }
}
