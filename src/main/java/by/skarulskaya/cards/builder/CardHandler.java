package by.skarulskaya.cards.builder;

import by.skarulskaya.cards.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

public class CardHandler extends DefaultHandler {
    private final Set<Card> cards;
    private final EnumSet<CardXmlTag> tagContent;
    private Card currentCard;
    private CardXmlTag currentTag;

    public CardHandler() {
        cards = new HashSet<>();
        tagContent = EnumSet.range(CardXmlTag.THEME, CardXmlTag.CRAFTING_TIME);
    }

    public Set<Card> getCards() {
        return new HashSet<>(cards);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        String sentCardTag = CardXmlTag.SENT_CARD.toString();
        String storeCardTag = CardXmlTag.STORE_CARD.toString();
        String handmadeCardTag = CardXmlTag.HANDMADE_CARD.toString();
        if (sentCardTag.equals(qName) || storeCardTag.equals(qName)
                || handmadeCardTag.equals(qName)) {
            currentCard = sentCardTag.equals(qName) ? new SentCard()
                    : storeCardTag.equals(qName) ? new StoreCard()
                    : new HandmadeCard();
            String cardId = null;
            String author = null;
            String textContent = null;
            if (attrs.getLength() == 1) {
                cardId = attrs.getValue(0);
            } else {
                String idTag = CardXmlTag.ID.toString();
                String authorTag = CardXmlTag.AUTHOR.toString();
                String textContentTag = CardXmlTag.TEXT_CONTENT.toString();
                for (int i = 0; i < attrs.getLength(); i++) {
                    String attrName = attrs.getLocalName(i).trim();
                    if (attrName.equals(idTag)) {
                        cardId = attrs.getValue(i);
                    }
                    if (attrName.equals(authorTag)) {
                        author = attrs.getValue(i);
                    }
                    if (attrName.equals(textContentTag)) {
                        textContent = attrs.getValue(i);
                    }
                }
            }
            currentCard.setId(cardId);
            currentCard.setAuthor(Optional.ofNullable(author));
            if (sentCardTag.equals(qName)) {
                ((SentCard) currentCard).setTextContent(Optional.ofNullable(textContent));
            }
        } else {
            String constantName = ConstantConverter.convert(qName);
            CardXmlTag tag = CardXmlTag.valueOf(constantName);
            if (tagContent.contains(tag)) {
                currentTag = tag;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length);
        if (currentTag != null) {
            switch (currentTag) {
                case THEME: {
                    String constantName = ConstantConverter.convert(data);
                    currentCard.setTheme(CardTheme.valueOf(constantName));
                    break;
                }
                case TYPE: {
                    String constantName = ConstantConverter.convert(data);
                    currentCard.setType(CardType.valueOf(constantName));
                    break;
                }
                case COUNTRY: {
                    String constantName = ConstantConverter.convert(data);
                    currentCard.setCountry(CardCountry.valueOf(constantName));
                    break;
                }
                case HAS_MELODY:
                    currentCard.setHasMelody(Boolean.parseBoolean(data));
                    break;
                case SENDING_TIME:
                    ((SentCard) currentCard).setSendingTime(LocalDateTime.parse(data));
                    break;
                case PRICE: {
                    if (currentCard instanceof StoreCard) {
                        ((StoreCard) currentCard).setPrice(Double.parseDouble(data));
                    } else {
                        ((HandmadeCard) currentCard).setPrice(Double.parseDouble(data));
                    }
                    break;
                }
                case PUBLICATION_YEAR:
                    ((StoreCard) currentCard).setPublicationYear(Year.parse(data));
                    break;
                case CRAFTING_TIME:
                    ((HandmadeCard) currentCard).setCraftingTime(Duration.parse(data));
                    break;
                default:
                    throw new EnumConstantNotPresentException(currentTag.getDeclaringClass(), currentTag.name());
            }
        }
        currentTag = null;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String sentCardTag = CardXmlTag.SENT_CARD.toString();
        String storeCardTag = CardXmlTag.STORE_CARD.toString();
        String handmadeCardTag = CardXmlTag.HANDMADE_CARD.toString();

        if (sentCardTag.equals(qName) || storeCardTag.equals(qName)
                || handmadeCardTag.equals(qName)) {
            cards.add(currentCard);
            currentCard = null;
        }
    }
}
