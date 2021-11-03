package by.skarulskaya.cards.entity;


import java.util.Optional;

public abstract class Card {
    private String id;
    private CardTheme theme;
    private CardType type;
    private CardCountry country;
    private Optional<String> author;
    private boolean hasMelody;

    public Card(){}

    public Card(String id, CardTheme theme, CardType type, CardCountry country, Optional<String> author, boolean hasMelody) {
        this.id = id;
        this.theme = theme;
        this.type = type;
        this.country = country;
        this.author = author;
        this.hasMelody = hasMelody;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CardTheme getTheme() {
        return theme;
    }

    public void setTheme(CardTheme theme) {
        this.theme = theme;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardCountry getCountry() {
        return country;
    }

    public void setCountry(CardCountry country) {
        this.country = country;
    }

    public Optional<String> getAuthor() {
        return author;
    }

    public void setAuthor(Optional<String> author) {
        this.author = author;
    }

    public boolean isHasMelody() {
        return hasMelody;
    }

    public void setHasMelody(boolean hasMelody) {
        this.hasMelody = hasMelody;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Card{");
        sb.append("id='").append(id).append('\'');
        sb.append(", theme=").append(theme);
        sb.append(", type=").append(type);
        sb.append(", country=").append(country);
        sb.append(", author=").append(author);
        sb.append(", hasMelody=").append(hasMelody);
        sb.append('}');
        return sb.toString();
    }

    public String toStringParams() {
        final StringBuffer sb = new StringBuffer("id='").append(id).append('\'');
        sb.append(", theme=").append(theme);
        sb.append(", type=").append(type);
        sb.append(", country=").append(country);
        sb.append(", author=").append(author);
        sb.append(", hasMelody=").append(hasMelody);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;

        Card card = (Card) o;

        if (hasMelody != card.hasMelody) return false;
        if (id != null ? !id.equals(card.id) : card.id != null) return false;
        if (theme!= null ? theme != card.theme : card.theme != null) return false;
        if (type!=null ? type != card.type: card.type != null) return false;
        if (country != null ? country != card.country : card.country != null) return false;
        return author != null ? author.equals(card.author) : card.author == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (theme != null ? theme.ordinal() : 0);
        result = 31 * result + (type != null ? type.ordinal() : 0);
        result = 31 * result + (country != null ? country.ordinal() : 0);
        if(author != null && author.isPresent()) {
            result = 31 * result + author.get().hashCode();
        }
        result = 31 * result + (hasMelody ? 1 : 0);
        return result;
    }
}

