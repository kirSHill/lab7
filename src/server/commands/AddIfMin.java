package server.commands;


import essentials.elements.City;
import essentials.interaction.Message;
import essentials.precommands.Precommand;

import java.sql.Connection;
import java.util.PriorityQueue;

public class AddIfMin extends Add {


    public AddIfMin(Precommand precommand, Connection connection) {
        super(precommand, connection);
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        this.city.setId();
        if (city.getPopulation() < collection.peek().getPopulation()) {
            this.city = cityRepository.saveGet(this.city);
            collection.add(city);
            return new Message(true, "В введённом Вами городе население ниже, чем в минимальном элементе коллекции, поэтому элемент добавлен в коллекцию.");
        } else {
            return new Message(true, "В введённом Вами городе население выше, чем в минимальном элементе коллекции. Выберите другое значение. (начните с 'addIfMin')");
        }
    }
}
