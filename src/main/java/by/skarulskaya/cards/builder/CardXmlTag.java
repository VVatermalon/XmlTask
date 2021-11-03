package by.skarulskaya.cards.builder;

import java.util.Locale;

public enum CardXmlTag {
    THEME, TYPE, COUNTRY, HAS_MELODY, SENDING_TIME, PRICE, PUBLICATION_YEAR, CRAFTING_TIME,
    ID, AUTHOR, TEXT_CONTENT,
    SENT_CARD, HANDMADE_CARD, STORE_CARD,
    OLD_CARDS;

    @Override
    public String toString() {
        final String UNDERSCORE = "_";
        StringBuilder sb = new StringBuilder(this.name().trim().toLowerCase());
        int underscoreIndex = sb.indexOf(UNDERSCORE);
        if(underscoreIndex != -1) {
            char letter = sb.charAt(underscoreIndex + 1);
            sb.replace(underscoreIndex, underscoreIndex + 2, String.valueOf(Character.toUpperCase(letter)));
        }
        return sb.toString();
    }
}
