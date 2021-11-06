package by.skarulskaya.cards.builder;

import by.skarulskaya.cards.exception.CardException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SaxCardBuilderTest {

    @Test
    public void testBuildCards() {
        try {
            SaxCardBuilder builder = new SaxCardBuilder();
            builder.buildCards();
        } catch (CardException e) {
            fail(e.getMessage());
        }
    }
}