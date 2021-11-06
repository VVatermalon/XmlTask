package by.skarulskaya.cards.builder;

import by.skarulskaya.cards.entity.Card;
import by.skarulskaya.cards.exception.CardException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCardBuilder {
    protected Set<Card> cards;

    public AbstractCardBuilder() {
        cards = new HashSet<>();
    }
    public AbstractCardBuilder(Set<Card> cards) {
        this.cards = cards;
    }

    public Set<Card> getCards() {
        return cards;
    }

    abstract public void buildCards(String xmlSrc) throws CardException;
}
