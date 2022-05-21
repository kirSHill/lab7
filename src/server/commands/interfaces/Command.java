package server.commands.interfaces;

import essentials.elements.City;
import essentials.interaction.Message;

import java.io.Serializable;
import java.util.PriorityQueue;

public interface Command extends Serializable {
    Message execute(PriorityQueue<City> collection);
}
