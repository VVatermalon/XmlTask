package by.skarulskaya.cards.entity;

import java.time.LocalDateTime;
import java.util.Optional;

public class SentCard extends Card {
    private LocalDateTime sendingTime;
    private String textContent;

    public SentCard(String id, CardTheme theme, CardType type, CardCountry country, Optional<String> author, boolean hasMelody, LocalDateTime sendingTime, String textContent) {
        super(id, theme, type, country, author, hasMelody);
        this.sendingTime = sendingTime;
        this.textContent = textContent;
    }

    public LocalDateTime getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(LocalDateTime sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SentCard{");
        sb.append(super.toStringParams());
        sb.append("sendingTime=").append(sendingTime);
        sb.append(", textContent='").append(textContent).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        if (!super.equals(o)) return false;

        SentCard sentCard = (SentCard) o;

        if (sendingTime != null ? !sendingTime.equals(sentCard.sendingTime) : sentCard.sendingTime != null)
            return false;
        return textContent != null ? textContent.equals(sentCard.textContent) : sentCard.textContent == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sendingTime != null ? sendingTime.hashCode() : 0);
        result = 31 * result + (textContent != null ? textContent.hashCode() : 0);
        return result;
    }
}
