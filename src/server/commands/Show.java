package server.commands;

import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;
import java.util.PriorityQueue;

public class Show implements Command {


    public Show() {
    }


    @Override
    public Message execute(PriorityQueue<City> collection) {
        StringBuilder stringBuilder;
        if (collection.size() == 0) {
            return new Message(true, "В коллекции нет элементов.");
        } else {
            stringBuilder = new StringBuilder("Все элементы коллекции:\n");
        }
        for (City city : collection) {
            stringBuilder.append(city.toString() + city.getName().length() + "\n");
        }
        return new Message(true, stringBuilder + "Всего: " + collection.size() + ".\n");
    }
}
