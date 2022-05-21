package server.commands;

import essentials.elements.City;
import essentials.elements.UserInfo;
import essentials.interaction.Message;
import server.Encryptor;
import server.commands.interfaces.Command;
import server.models.UserModel;

import java.sql.Connection;
import java.util.PriorityQueue;

public class Authorize implements Command {

    private UserInfo userInfo;
    private Connection connection;
    private UserModel userModel = new UserModel();

    public Authorize(UserInfo userInfo, Connection connection) {
        this.userInfo = userInfo;
        this.connection = connection;
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        UserInfo newUser = userModel.findByLogin(connection, userInfo.getLogin());
        if (newUser != null && newUser.getPassword().equals(Encryptor.encryptPassword(userInfo.getPassword()))) {
            return new Message(true,"Авторизация пройдена! Добро пожаловать, " + userInfo.getLogin());
        } else {
            return new Message(false,"Авторизация не пройдена!");
        }
    }
}
