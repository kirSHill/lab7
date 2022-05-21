package server.commands;

import essentials.precommands.Precommand;
import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;
import server.repositories.CityRepository;
import server.repositories.UserRepository;

import java.sql.Connection;
import java.util.PriorityQueue;

public class RemoveFirst implements Command {

    private String client;
    private Connection connection;

    public RemoveFirst(Precommand precommand, Connection connection) {
        this.client = precommand.getClient();
        this.connection = connection;
    }


    @Override
    public Message execute(PriorityQueue<City> collection) {
        if (collection.size() == 0) {
            return new Message(true, "В коллекции нет элементов.");
        }
        UserRepository userRepository = new UserRepository(connection);
        CityRepository cityRepository = new CityRepository(connection);
        PriorityQueue<City> helper = new PriorityQueue<>();
        int size = collection.size() + 1;
        for (int i = 1; i < size; i++) {
            City city = collection.peek();
            if (cityRepository.getById(city.getId()).getClientId() == userRepository.getByLogin(client).getId()) {
                if (cityRepository.deleteById(city.getId())) {
                    System.out.println("");
                    collection.remove();
                    break;
                }
            } else {
                helper.add(city);
                }
            }
        while (!helper.isEmpty()) {
            collection.add(helper.remove());
        }
        return new Message(true, "Элемент успешно удалён.");
    }
}
