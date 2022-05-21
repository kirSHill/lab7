package server.commands;

import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class PrintDescending implements Command {


    public PrintDescending() {}


    @Override
    public Message execute(PriorityQueue<City> collection) {
        ArrayList<City> reverse = new ArrayList<>(collection);
        Collections.reverse(reverse);
        StringBuilder stringBuilder = new StringBuilder();
        for (City city : reverse) {
            stringBuilder.append(city.toString() + "\n");
        }
        return new Message(true, stringBuilder.toString());
    }
}
