package server.commands.interfaces;

import essentials.elements.City;
import essentials.interaction.Message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.PriorityQueue;

public interface Date extends Command {
    Message execute(PriorityQueue<City> collection, LocalDateTime initDate);
}
