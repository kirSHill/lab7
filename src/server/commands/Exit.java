package server.commands;

import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;
import java.util.PriorityQueue;

public class Exit implements Command {


    @Override
    public Message execute(PriorityQueue<City> collection){
        return new Message(false,"");
    }
}
