package server.commands;

import essentials.precommands.Precommand;
import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;
import server.repositories.CityRepository;
import server.repositories.UserRepository;

import java.sql.Connection;
import java.util.PriorityQueue;
import java.util.Stack;

public class Clear implements Command {

    private String client;
    private Connection connection;

    public Clear(Precommand precommand, Connection connection) {
        this.client = precommand.getClient();
        this.connection = connection;
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        UserRepository userRepository = new UserRepository(connection);
        CityRepository cityRepository = new CityRepository(connection);
        PriorityQueue<City> helper = new PriorityQueue<>();
        int size = collection.size() + 1;
        for (int i = 1; i < size; i++) {
            City city = collection.remove();
            if (cityRepository.getById(city.getId()).getClientId() != userRepository.getByLogin(client).getId()) {
                helper.add(city);
            } else {
                if (cityRepository.deleteById(city.getId())) {
                    System.out.println("");
                }
            }
        }
            while (!helper.isEmpty()) {
                collection.add(helper.remove());
            }
        return new Message(true, "Все Ваши элементы удалены.");
    }
}
