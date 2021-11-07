package by.skarulskaya.cards.builder;

import by.skarulskaya.cards.entity.*;
import by.skarulskaya.cards.exception.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class StAXCardBuilder extends AbstractCardBuilder {
    private static final Logger logger = LogManager.getLogger();
    private final XMLInputFactory inputFactory;

    public StAXCardBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }
    public StAXCardBuilder(Set<Card> cards) {
        super(cards);
        inputFactory = XMLInputFactory.newInstance();
    }
    @Override
    public void buildCards(String xmlSrc) throws CardException {
        if(xmlSrc == null) {
            logger.warn("Null parameter");
            return;
        }
        try (FileInputStream inputStream = new FileInputStream(xmlSrc)) {
            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
            String name;
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    String sentCardTag = CardXmlTag.SENT_CARD.toString();
                    String storeCardTag = CardXmlTag.STORE_CARD.toString();
                    String handCardTag = CardXmlTag.HANDMADE_CARD.toString();

                    if (sentCardTag.equals(name) || storeCardTag.equals(name)
                            || handCardTag.equals(name)) {
                        Card card = buildCard(reader);
                        cards.add(card);
                    }
                }
            }
        } catch (IOException e) {
            throw new CardException("Unable to open XML file " + xmlSrc, e);
        } catch (XMLStreamException e) {
            throw new CardException("Error during parsing XML file " + xmlSrc, e);
        }
    }

    private static Card buildCard(XMLStreamReader reader) throws XMLStreamException {
        String cardName = reader.getLocalName();
        CardXmlTag tag = CardXmlTag.valueOf(ConstantConverter.convert(cardName));

        Card card = tag.equals(CardXmlTag.SENT_CARD) ? new SentCard()
                : tag.equals(CardXmlTag.STORE_CARD) ? new StoreCard()
                : new HandmadeCard();
        card.setId(reader.getAttributeValue(null, CardXmlTag.ID.toString()));
        String authorValue = reader.getAttributeValue(null, CardXmlTag.AUTHOR.toString());
        card.setAuthor(Optional.ofNullable(authorValue));
        if(card instanceof SentCard) {
            String textValue = reader.getAttributeValue(null, CardXmlTag.TEXT_CONTENT.toString());
            ((SentCard) card).setTextContent(Optional.ofNullable(textValue));
        }

        String name;
        while(reader.hasNext()) {
            int type = reader.next();
            switch(type) {
                case XMLStreamConstants.START_ELEMENT: {
                    name = reader.getLocalName();
                    switch (CardXmlTag.valueOf(ConstantConverter.convert(name))) {
                        case THEME: {
                            name = ConstantConverter.convert(getXmlText(reader));
                            card.setTheme(CardTheme.valueOf(name));
                            break;
                        }
                        case TYPE: {
                            name = ConstantConverter.convert(getXmlText(reader));
                            card.setType(CardType.valueOf(name));
                            break;
                        }
                        case COUNTRY: {
                            name = ConstantConverter.convert(getXmlText(reader));
                            card.setCountry(CardCountry.valueOf(name));
                            break;
                        }
                        case HAS_MELODY:
                            card.setHasMelody(Boolean.parseBoolean(getXmlText(reader)));
                            break;
                        case SENDING_TIME:
                            ((SentCard) card).setSendingTime(LocalDateTime.parse(getXmlText(reader)));
                            break;
                        case PRICE: {
                            if (card instanceof StoreCard) {
                                ((StoreCard) card).setPrice(Double.parseDouble(getXmlText(reader)));
                            } else {
                                ((HandmadeCard) card).setPrice(Double.parseDouble(getXmlText(reader)));
                            }
                            break;
                        }
                        case PUBLICATION_YEAR:
                            ((StoreCard) card).setPublicationYear(Year.parse(getXmlText(reader)));
                            break;
                        case CRAFTING_TIME:
                            ((HandmadeCard) card).setCraftingTime(Duration.parse(getXmlText(reader)));
                            break;
                    }
                    break;
                }//todo no default
                case XMLStreamConstants.END_ELEMENT: {
                    name = reader.getLocalName();
                    String sentCardTag = CardXmlTag.SENT_CARD.toString();
                    String storeCardTag = CardXmlTag.STORE_CARD.toString();
                    String handCardTag = CardXmlTag.HANDMADE_CARD.toString();
                    if (sentCardTag.equals(name) || storeCardTag.equals(name) || handCardTag.equals(name)) {
                        return card;
                    }
                }
            }
        }
        throw new XMLStreamException("Smth wrong");
    }

    private static String getXmlText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if(reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
