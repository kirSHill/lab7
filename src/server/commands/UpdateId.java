package server.commands;

import essentials.precommands.ObjectIdPrecommand;
import essentials.precommands.Precommand;
import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;
import server.repositories.CityRepository;
import java.sql.Connection;
import java.util.PriorityQueue;

public class UpdateId extends Add implements Command {


    private final String argument;
    private Connection connection;


    public UpdateId(Precommand precommand, Connection connection) {
        super(precommand, connection);
        this.connection = connection;
        ObjectIdPrecommand objectIdPrecommand = (ObjectIdPrecommand) precommand;
        this.argument = objectIdPrecommand.getId();
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        city.setId(Integer.parseInt(argument));
        int size = collection.size() + 1;
        PriorityQueue<City> helper = new PriorityQueue<>();
        CityRepository cityRepository = new CityRepository(connection);
        if (cityRepository.updateById(Integer.parseInt(argument), city)) {
            for (int i = 1; i < size; i++) {
                city = collection.remove();
                if (city.getId() != Integer.parseInt(argument)) {
                    helper.add(city);
                } else {
                    break;
                }
            }
            for (City cityy : helper) {
                while (!helper.isEmpty()) {
                    cityy = helper.remove();
                    collection.add(cityy);
                }
            }
            collection.add(city);
        }
        return new Message(true, "Элемент с id " + argument + " успешно обновлён!");
    }
}