package by.skarulskaya.cards.entity;

import java.time.Year;
import java.util.Optional;

public class StoreCard extends Card {
    private double price;
    private int remainsCount;
    private Year publicationYear;

    public StoreCard(String id, CardTheme theme, CardType type, CardCountry country, Optional<String> author, boolean hasMelody, double price, int remainsCount, Year publicationYear) {
        super(id, theme, type, country, author, hasMelody);
        this.price = price;
        this.remainsCount = remainsCount;
        this.publicationYear = publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRemainsCount() {
        return remainsCount;
    }

    public void setRemainsCount(int remainsCount) {
        this.remainsCount = remainsCount;
    }

    public Year getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Year publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StoreCard{");
        sb.append(super.toStringParams());
        sb.append("price=").append(price);
        sb.append(", remainsCount=").append(remainsCount);
        sb.append(", publicationYear=").append(publicationYear);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        if (!super.equals(o)) return false;

        StoreCard storeCard = (StoreCard) o;

        if (Double.compare(storeCard.price, price) != 0) return false;
        if (remainsCount != storeCard.remainsCount) return false;
        return publicationYear != null ? publicationYear.equals(storeCard.publicationYear) : storeCard.publicationYear == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + remainsCount;
        result = 31 * result + (publicationYear != null ? publicationYear.hashCode() : 0);
        return result;
    }
}
