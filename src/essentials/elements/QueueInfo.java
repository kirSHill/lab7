package essentials.elements;
import essentials.elements.City;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.PriorityQueue;

/**
 * Класс информации о коллекции для xml
 */
public class QueueInfo implements Serializable {


    /**
     * Поля класса
     */
    private static PriorityQueue<City> collection;
    private int maxId;
    private LocalDateTime creationDate;
    private LocalDateTime initDate;


    /**
     * Конструктор класса
     * @param collection - значение поля collection
     * @param maxId - значение поля maxId
     * @param creationDate - значение поля creationDate
     * @throws Exception - исключение о пустых полях класса
     */
    public QueueInfo(PriorityQueue<City> collection, int maxId, LocalDateTime creationDate, LocalDateTime initDate) throws Exception {
        if (collection != null && maxId != 0 && creationDate != null && initDate != null) {
            this.collection = collection;
            this.maxId = maxId;
            this.creationDate = creationDate;
            this.initDate = initDate;
        } else {
            throw new Exception("Параметры не могут быть null.");
        }
    }

    public QueueInfo(){}

    /**
     * Получение коллекции
     * @return - коллекция
     */
    public static PriorityQueue<City> getCollection() {
        return collection;
    }
    /**
     * Получение maxId
     * @return - максимальный id в коллекции
     */
    public int getMaxId() {
        return maxId;
    }
    /**
     * Получение даты создания
     * @return - дата создания файла
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public LocalDateTime getInitDate() {
        return initDate;
    }
    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }
}

