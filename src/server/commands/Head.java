package server.commands;

import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;

import java.util.PriorityQueue;

public class Head implements Command {


    public Head() {}


    @Override
    public Message execute(PriorityQueue<City> collection) {
        return new Message(true,"Первый элемент коллекции: \n" + collection.peek());
    }
}
