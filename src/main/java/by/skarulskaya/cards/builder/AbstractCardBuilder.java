package by.skarulskaya.cards.builder;

import by.skarulskaya.cards.entity.Card;
import by.skarulskaya.cards.exception.CardException;

import java.util.Set;

public abstract class AbstractCardBuilder {
    protected Set<Card> cards;
    abstract void buildCards(String xmlSrc) throws CardException;
    abstract Set<Card> getCards();
}
