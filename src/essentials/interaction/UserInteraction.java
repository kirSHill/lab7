package essentials.interaction;

import java.io.Serializable;

/**
 * Интерфейс для получения информации от пользователя
 */
public interface UserInteraction extends Serializable {
    void print(boolean newLine, String message);
    String getData();
    String getSecureData();
}
