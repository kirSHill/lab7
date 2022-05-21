package client;

import essentials.precommands.*;
import essentials.interaction.UserInteraction;

import java.util.ArrayList;

public abstract class Precommander {
    public static Precommand getCommand(String input, boolean fromScript, UserInteraction interaction) {
        input = input.trim();
        String[] commandParts = input.split("\\s+");
        String command = commandParts[0];
        ArrayList<String> arguments = new ArrayList<>();
        for (int i = 1; i < commandParts.length; i++) {
            String argument = commandParts[i].replaceAll("\\s+", "");
            if (!argument.isEmpty()) {
                arguments.add(argument);
            }
        }

        switch (command) {
            case "register":
                ObjectPrecommand registerPrecommand = new ObjectPrecommand("register");
                registerPrecommand.preprocess(Authorizer.getUserInfo(interaction),false);
                return registerPrecommand;
            case "authorize":
                ObjectPrecommand authorizePrecommand = new ObjectPrecommand("authorize");
                authorizePrecommand.preprocess(Authorizer.getUserInfo(interaction),false);
                return authorizePrecommand;
            case "help":
                BasicPrecommand helpPrecommand = new BasicPrecommand("help");
                helpPrecommand.preprocess(null,fromScript);
                return helpPrecommand;
            case "info":
                BasicPrecommand infoPrecommand = new BasicPrecommand("info");
                infoPrecommand.preprocess(null,fromScript);
                return infoPrecommand;
            case "show":
                BasicPrecommand showPrecommand = new BasicPrecommand("show");
                showPrecommand.preprocess(null,fromScript);
                return showPrecommand;
            case "add":
                ObjectPrecommand addPrecommand = new ObjectPrecommand("add");
                addPrecommand.preprocess(CityGenerator.createCity(interaction,fromScript),fromScript);
                return addPrecommand;
            case "clear":
                BasicPrecommand clearPrecommand = new BasicPrecommand("clear");
                clearPrecommand.preprocess(null, fromScript);
                return clearPrecommand;
            case "removeById":
                if(arguments.size() == 0) {
                    interaction.print(true, "Отсутствуют необходимые параметры.");
                    return null;
                }
                IdPrecommand removeByIdPrecommand = new IdPrecommand("removeById", arguments.get(0));
                removeByIdPrecommand.preprocess(null,fromScript);
                return removeByIdPrecommand;
            case "updateId":
                if(arguments.size() == 0) {
                    interaction.print(true, "Отсутствуют необходимые параметры.");
                    return null;
                }
                ObjectIdPrecommand updateIdPrecommand = new ObjectIdPrecommand("updateId", arguments.get(0));
                updateIdPrecommand.preprocess(CityGenerator.createCity(interaction,fromScript),fromScript);
                return updateIdPrecommand;
            case "executeScript":
                if(fromScript) {
                    interaction.print(true, "Запрещено использовать скрипт из другого скрипта!");
                    return null;
                }
                if (arguments.size() == 0) {
                    interaction.print(true, "Отстутствуют необходимые параметры.");
                    return null;
                }
                BasicPrecommand executeScriptPrecommand = new BasicPrecommand("executeScript");
                executeScriptPrecommand.preprocess(arguments.get(0),false);
                return executeScriptPrecommand;
            case "exit":
                BasicPrecommand exitPrecommand = new BasicPrecommand("exit");
                exitPrecommand.preprocess(null, fromScript);
                return exitPrecommand;
            case "removeFirst":
                BasicPrecommand removeFirstPrecommand = new BasicPrecommand("removeFirst");
                removeFirstPrecommand.preprocess(null, fromScript);
                return removeFirstPrecommand;
            case "head":
                BasicPrecommand headPrecommand = new BasicPrecommand("head");
                headPrecommand.preprocess(null, fromScript);
                return headPrecommand;
            case "addIfMin":
                ObjectPrecommand addIfMinPrecommand = new ObjectPrecommand("addIfMin");
                addIfMinPrecommand.preprocess(CityGenerator.createCity(interaction,fromScript),fromScript);
                return addIfMinPrecommand;
            case "countByClimate":
                if (arguments.size() == 0) {
                    interaction.print(true, "Отсутствуют необходимые параметры.");
                    return null;
                }
                BasicPrecommand countByClimate = new BasicPrecommand("countByClimate");
                String argument = arguments.get(0);
                countByClimate.preprocess(argument,fromScript);
                return countByClimate;
            case "countGreaterThanGovernment":
                if (arguments.size() == 0) {
                    interaction.print(true, "Отсутствуют необходимые параметры.");
                    return null;
                }
                BasicPrecommand countGreaterThanGovernment = new BasicPrecommand("countGreaterThanGovernment");
                String arg = arguments.get(0);
                countGreaterThanGovernment.preprocess(arg,false);
                return countGreaterThanGovernment;
            case "printDescending":
                ObjectPrecommand printDescendingPrecommand = new ObjectPrecommand("printDescending");
                printDescendingPrecommand.preprocess(null,fromScript);
                return printDescendingPrecommand;
            case "":
                return null;
            default:
                interaction.print(true,"Команды '" + command + "' не существует. " +
                        "Воспользуйтесь 'help' для получения списка команд.");
                return null;
        }
    }
}
