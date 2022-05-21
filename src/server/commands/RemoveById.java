package server.commands;

import essentials.precommands.IdPrecommand;
import essentials.precommands.Precommand;
import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;
import essentials.interaction.UserInteraction;
import server.repositories.CityRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class RemoveById implements Command {


    private final String argument;
    private String client;
    private Connection connection;


    public RemoveById(Connection connection, ArrayList<String> args) {
        this.argument = args.get(0);
        this.connection = connection;
    }

    public RemoveById(String argument, Connection connection) {
        this.connection = connection;
        this.argument = argument;
    }

    public RemoveById(IdPrecommand precommand, Connection connection) {
        this.connection = connection;
        this.argument = precommand.getId();
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        search(collection, argument);
        return new Message(true, "Элемент с id " + argument + " был удалён из коллекции.");
    }

    public Message search(PriorityQueue<City> collection, String argument) {

        int count = 0;

        for (City cityy : collection) {
            if (cityy.getId() == Integer.parseInt(argument)) {
                count++;
            }
        }
        switch(count) {
            case 0:
                return new Message(true,"Элемент с id " + argument + " отсутствует в коллекции. \nВоспользуйтесь 'show' для просмотра всех элементов коллекции.");
            case 1:
                remove(collection, argument);
                break;
            default:
                return new Message(true,"В коллекции несколько элементов с одинаковыми id.");
        }
        return new Message(false, "");
    }

    public Message remove(PriorityQueue<City> collection, String argument) {
        CityRepository cityRepository = new CityRepository(connection);
        int size = collection.size() + 1;
        PriorityQueue<City> helper = new PriorityQueue<>();
        City city;

        for (int i = 1; i < size; i++) {
            city = collection.remove();
            if (city.getId() != Integer.parseInt(argument)) {
                helper.add(city);
            } else {
                if (cityRepository.deleteById(city.getId())) {
                    System.out.println("");
                }
                break;
            }
        }
        for (City cityy : helper) {
            while (!helper.isEmpty()) {
                cityy = helper.remove();
                collection.add(cityy);
            }
        }

        return new Message(true,"Элемент с id " + argument + " был удалён из коллекции.");

    }
}
