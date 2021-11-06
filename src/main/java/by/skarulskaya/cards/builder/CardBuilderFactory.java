package by.skarulskaya.cards.builder;

import by.skarulskaya.cards.exception.CardException;

public class CardBuilderFactory {
    private enum BuilderType {
        SAX, STAX, DOM
    }
    private CardBuilderFactory() {}
    public static AbstractCardBuilder getBuilder(String builderName) throws CardException {
        BuilderType type;
        try {
            type = BuilderType.valueOf(builderName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CardException("Invalid builder name " + builderName, e);
        }
        switch (type) {
            case SAX:
                return new SaxCardBuilder();
            case STAX:
                return new StAXCardBuilder();
            case DOM:
                return new DOMCardBuilder();
            default:
                throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }
}
