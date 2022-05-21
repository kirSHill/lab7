package server.commands;

import essentials.elements.City;
import essentials.elements.UserInfo;
import essentials.precommands.*;
import server.commands.interfaces.Command;
import server.commands.*;
import server.commands.Help;
import server.commands.Info;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class Commander {

    /**
     * Метод работы с вводимыми пользователем командами
     */
    public static Command getCommand(Precommand precommand, Connection connection) {
        switch(precommand.getCommandName()) {
            case "register":
                if (precommand instanceof ObjectPrecommand && precommand.getArgument() instanceof UserInfo) {
                    return new Register((UserInfo) precommand.getArgument(), connection);
                }
                return null;
            case "authorize":
                if (precommand instanceof ObjectPrecommand && precommand.getArgument() instanceof UserInfo) {
                    return new Authorize((UserInfo) precommand.getArgument(), connection);
                }
                return null;
            case "help":
                return new Help();
            case "info":
                return new Info();
            case "show":
                return new Show();
            case "add":
                if (precommand instanceof ObjectPrecommand && precommand.getArgument() instanceof City) {
                    return new Add(precommand, connection);
                }
            case "clear":
                return new Clear(precommand, connection);
            case "removeById":
                if(precommand instanceof IdPrecommand) {
                    return new RemoveById((IdPrecommand) precommand, connection);
                }
                return null;
            case "updateId":
                if(precommand instanceof ObjectIdPrecommand) {
                    return new UpdateId(precommand, connection);
                }
                return null;
            case "removeFirst":
                return new RemoveFirst(precommand, connection);
            case "head":
                return new Head();
            case "addIfMin":
                return new AddIfMin(precommand, connection);
            case "countByClimate":
                if (precommand instanceof BasicPrecommand && precommand.getArgument() instanceof String) {
                    return new CountByClimate((String) precommand.getArgument());
                }
                return null;
            case "countGreaterThanGovernment":
                if (precommand instanceof BasicPrecommand && precommand.getArgument() instanceof String) {
                    return new CountGreaterThanGovernment((String) precommand.getArgument());
                }
                return null;
            case "printDescending":
                return new PrintDescending();
            default:
                return null;
        }
    }
}
