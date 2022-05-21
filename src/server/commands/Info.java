package server.commands;

import server.commands.interfaces.Date;
import essentials.elements.City;
import essentials.interaction.Message;
import server.repositories.CityRepository;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class Info implements Date {

    public Info() {
    }


    @Override
    public Message execute(PriorityQueue<City> collection, LocalDateTime initDate) {
        return new Message(true, "Информация о коллекции: \n" +
                "Тип: " + City.class.getName() + "\n" +
                "Дата инициализации: " + initDate + "\n" +
                "Количество элементов: " + collection.size() + "\n");
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        return new Message(true,"");
    }
}
