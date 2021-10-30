package by.skarulskaya.cards.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@XmlRootElement(name = "Old Cards")
public class OldCards {
    private ArrayList<Card> cards = new ArrayList<>();

    public OldCards() {}

    public OldCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    static void Hello(){}

    public ArrayList<Card> getCards() {
        return (ArrayList<Card>)Collections.unmodifiableList(cards);
    }

    public Optional<Card> getCardById(int index) {
        if(index<0 || index >= cards.size()) {
            return Optional.empty();
        }
        return Optional.of(cards.get(index));
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addCards(ArrayList<Card> cards) {
        if(cards == null) {
            return;
        }
        this.cards.addAll(cards);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void clearCards() {
        cards.clear();
    }
}
