package by.skarulskaya.cards.builder;

import by.skarulskaya.cards.entity.*;
import by.skarulskaya.cards.exception.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DOMCardBuilder extends AbstractCardBuilder {
    static final Logger logger = LogManager.getLogger();
    private Set<Card> cards;
    private DocumentBuilder docBuilder;

    public DOMCardBuilder() {
        cards = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Error during DOM parser configuration", e);
        }
    }
    @Override
    public void buildCards(String xmlSrc) throws CardException {
        Document doc = null;
        try {
            doc = docBuilder.parse(xmlSrc);
            Element root = doc.getDocumentElement();
            NodeList cardList = root.getChildNodes(); // TODO: 04.11.2021 тут другое название элемента, даже несколько будет
            for(int i=0; i<cardList.getLength(); i++) {
                if(cardList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element cardElement = (Element) cardList.item(i);
                    Card card = buildCard(cardElement);
                    cards.add(card);
                }
            }
        } catch (IOException | SAXException e) {
            logger.error(e);
        }
    }

    private static Card buildCard(Element cardElement) {
        String tagName = ConstantConverter.convert(cardElement.getTagName());
        CardXmlTag tag = CardXmlTag.valueOf(tagName);
        Card card;
        switch(tag) {
            case SENT_CARD: {
                card = new SentCard();
                SentCard sentCard = (SentCard) card;
                String timeContent = getElementTextContent(cardElement, CardXmlTag.SENDING_TIME.toString());
                sentCard.setSendingTime(LocalDateTime.parse(timeContent));
                String textContent = cardElement.getAttribute(CardXmlTag.TEXT_CONTENT.toString());
                sentCard.setTextContent(textContent.isEmpty()?Optional.empty():Optional.of(textContent));
                break;
            }
            case STORE_CARD: {
                card = new StoreCard();
                StoreCard sentCard = (StoreCard) card;
                String priceContent = getElementTextContent(cardElement, CardXmlTag.PRICE.toString());
                sentCard.setPrice(Double.parseDouble(priceContent));
                String yearContent = getElementTextContent(cardElement, CardXmlTag.PUBLICATION_YEAR.toString());
                sentCard.setPublicationYear(Year.parse(yearContent));
                break;
            }
            case HANDMADE_CARD: {
                card = new HandmadeCard();
                HandmadeCard sentCard = (HandmadeCard) card;
                String priceContent = getElementTextContent(cardElement, CardXmlTag.PRICE.toString());
                sentCard.setPrice(Double.parseDouble(priceContent));
                String timeContent = getElementTextContent(cardElement, CardXmlTag.CRAFTING_TIME.toString());
                sentCard.setCraftingTime(Duration.parse(timeContent));
                break;
            }
            default: throw new EnumConstantNotPresentException(tag.getDeclaringClass(), tag.name());
        }
        String themeContent = getElementTextContent(cardElement, CardXmlTag.THEME.toString());
        card.setTheme(CardTheme.valueOf(ConstantConverter.convert(themeContent)));
        String typeContent = getElementTextContent(cardElement, CardXmlTag.TYPE.toString());
        card.setType(CardType.valueOf(ConstantConverter.convert(typeContent)));
        String countryContent = getElementTextContent(cardElement, CardXmlTag.COUNTRY.toString());
        card.setCountry(CardCountry.valueOf(ConstantConverter.convert(countryContent)));
        String melodyContent = getElementTextContent(cardElement, CardXmlTag.HAS_MELODY.toString());
        card.setHasMelody(Boolean.parseBoolean(melodyContent));
        String idContent = cardElement.getAttribute(CardXmlTag.ID.toString());
        card.setId(idContent);
        String authorContent = cardElement.getAttribute(CardXmlTag.AUTHOR.toString());
        card.setAuthor(authorContent.isEmpty()?Optional.empty():Optional.of(authorContent));
        return card;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nodes = element.getElementsByTagName(elementName);
        Node node = nodes.item(0);
        String text  = node.getTextContent();
        return text;
    }

    @Override
    Set<Card> getCards() {
        return cards;
    }
}
