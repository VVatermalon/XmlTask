package by.skarulskaya.cards.builder;

import by.skarulskaya.cards.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

public class CardHandler extends DefaultHandler {
    static final Logger logger = LogManager.getLogger();
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
        logger.info("Enum toString sentCard "+sentCardTag);
        String storeCardTag = CardXmlTag.STORE_CARD.toString();
        logger.info("Enum toString store "+storeCardTag);
        String handmadeCardTag = CardXmlTag.HANDMADE_CARD.toString();
        logger.info("Enum toString hand "+handmadeCardTag);
        logger.info("qname = " + qName);
        if (sentCardTag.equals(qName) || storeCardTag.equals(qName)
                || handmadeCardTag.equals(qName)) {
            currentCard = sentCardTag.equals(qName) ? new SentCard()
                    : storeCardTag.equals(qName) ? new StoreCard()
                    : new HandmadeCard();
            logger.info("One of cards "+qName);
            String cardId = null;
            String author = null;
            String textContent = null;
            if (attrs.getLength() == 1) {
                logger.info("One attr "+attrs.getValue(0));
                cardId = attrs.getValue(0);
            } else {
                String idTag = CardXmlTag.ID.toString();
                String authorTag = CardXmlTag.AUTHOR.toString();
                String textContentTag = CardXmlTag.TEXT_CONTENT.toString();
                logger.warn("TEXT CONTENT TAG "+textContentTag);
                for (int i = 0; i < attrs.getLength(); i++) {
                    String attrName = attrs.getLocalName(i).trim();
                    if (attrName.equals(idTag)) {
                        logger.info("Many attr, one is id "+attrs.getValue(i));
                        cardId = attrs.getValue(i);
                    }
                    if (attrName.equals(authorTag)) {
                        logger.info("Many attr, one is author "+attrs.getValue(i));
                        author = attrs.getValue(i);
                    }
                    if (attrName.equals(textContentTag)) {
                        logger.info("Many attr, one is textContent "+attrs.getValue(i));
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
            String constantName = toConstantName(qName);
            CardXmlTag tag = CardXmlTag.valueOf(constantName);
            logger.info("Smth interesting "+tag);
            if (tagContent.contains(tag)) {
                currentTag = tag;
                logger.info("This is text tag! "+tag);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length);
        if (currentTag != null) {
            switch (currentTag) {
                case THEME: {
                    String constantName = dataToConstantName(data);
                    logger.info("theme constant "+constantName);
                    currentCard.setTheme(CardTheme.valueOf(constantName));
                    break;
                }
                case TYPE: {
                    String constantName = dataToConstantName(data);
                    logger.info("type constant "+constantName);
                    currentCard.setType(CardType.valueOf(constantName));
                    break;
                }
                case COUNTRY: {
                    String constantName = dataToConstantName(data);
                    logger.info("country constant "+constantName);
                    currentCard.setCountry(CardCountry.valueOf(constantName));
                    break;
                }
                case HAS_MELODY:
                    logger.info("melody constant "+data);
                    currentCard.setHasMelody(Boolean.parseBoolean(data));
                    break;
                case SENDING_TIME:
                    logger.info("sendingtime constant "+data);
                    ((SentCard) currentCard).setSendingTime(LocalDateTime.parse(data));
                    break;
                case PRICE: {
                    logger.info("price constant "+data);
                    if (currentCard instanceof StoreCard) {
                        ((StoreCard) currentCard).setPrice(Double.parseDouble(data));
                    } else {
                        ((HandmadeCard) currentCard).setPrice(Double.parseDouble(data));
                    }
                    break;
                }
                case PUBLICATION_YEAR:
                    logger.info("publicationyear constant "+data);
                    ((StoreCard) currentCard).setPublicationYear(Year.parse(data));
                    break;
                case CRAFTING_TIME:
                    logger.info("craftingtime constant "+data);
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
            logger.info("card finished! "+qName);
            cards.add(currentCard);
            currentCard = null;
        }
    }

    private String toConstantName(String string) {
        logger.info("toconstantName method " + string);
        StringBuilder sb = new StringBuilder(string.trim());
        for (int i = 0; i < sb.length(); i++) { //todo while
            if (Character.isUpperCase(sb.charAt(i))) {
                sb.insert(i, "_");
                logger.info("found upper symbol "+i);
                break;
            }
        }
        logger.info(sb);
        return sb.toString().toUpperCase();
    }

    private String dataToConstantName(String string) {
        return string.trim().toUpperCase();
    }
}
