package by.skarulskaya.cards.entity;

import java.time.Duration;
import java.util.Optional;

public class HandmadeCard extends Card {
    private double price;
    private Duration craftingTime;

    public HandmadeCard(){}

    public HandmadeCard(String id, CardTheme theme, CardType type, CardCountry country, Optional<String> author, boolean hasMelody, double price, Duration craftingTime) {
        super(id, theme, type, country, author, hasMelody);
        this.price = price;
        this.craftingTime = craftingTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Duration getCraftingTime() {
        return craftingTime;
    }

    public void setCraftingTime(Duration craftingTime) {
        this.craftingTime = craftingTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HandmadeCard{");
        sb.append(super.toStringParams());
        sb.append(", price=").append(price);
        sb.append(", craftingTime=").append(craftingTime);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        if (!super.equals(o)) return false;

        HandmadeCard that = (HandmadeCard) o;

        if (Double.compare(that.price, price) != 0) return false;
        return craftingTime != null ? craftingTime.equals(that.craftingTime) : that.craftingTime == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (craftingTime != null ? craftingTime.hashCode() : 0);
        return result;
    }
}
