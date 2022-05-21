package client;

import essentials.elements.UserInfo;
import essentials.interaction.UserInteraction;

public abstract class Authorizer {

    public static UserInfo getUserInfo(UserInteraction interaction) {
        String login = "";
        String password = "";
        while (login.isEmpty()) {
            interaction.print(false,"Введите логин: ");
            login = interaction.getData();
        }
        while (password.isEmpty()) {
            interaction.print(false,"Введите пароль: ");
            password = interaction.getData();
        }
        return new UserInfo(login, password);
    }
}
