package server.commands;

import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.elements.Government;
import essentials.interaction.Message;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class CountGreaterThanGovernment implements Command {


    private final String argument;


    public CountGreaterThanGovernment(String args) {
        this.argument = args;
    }


    @Override
    public Message execute(PriorityQueue<City> collection) {
        Government government = Government.getByName(argument);

        String result = collection.stream().filter((city) -> city.getGovernment().toString().length() > government.toString().length()).collect(Collectors.toList()).toString();
        return new Message(true, result);
    }
}
