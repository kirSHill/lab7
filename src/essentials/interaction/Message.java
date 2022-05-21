package essentials.interaction;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    private final String text;
    private final LocalDateTime creationDate;
    private final boolean result;

    public Message(boolean result, String text) {
        this.text = text;
        this.creationDate = LocalDateTime.now();
        this.result = result;
    }

    public String getText() {return text;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public boolean isSuccessful() {return this.result;}
}
