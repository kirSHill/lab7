package server.models;

import essentials.elements.UserInfo;
import server.Encryptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

public class UserModel extends Model {

    public UserModel() {
        this.name = "Users";
        this.fields = Arrays.asList(new FieldDB("id", "SERIAL PRIMARY KEY"),
                new FieldDB("login", "varchar(256)"),
                new FieldDB("password", "varchar(256)"));
    }

    public UserInfo findById(Connection connection, int id){
        PreparedStatement statement;
        StringBuilder createSql = new StringBuilder();
        try {
            createSql.append(String.format("SELECT * FROM %s WHERE id='%s'", this.name, id));
            statement = connection.prepareStatement(createSql.toString());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new UserInfo(rs.getString("login"), rs.getString("password"), id);
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public UserInfo findByLogin(Connection connection, String login) {
        PreparedStatement statement;
        StringBuilder createSql = new StringBuilder();
        try {
            createSql.append(String.format("SELECT * FROM %s WHERE login='%s'", this.name, login));
            statement = connection.prepareStatement(createSql.toString());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new UserInfo(login, rs.getString("password"), rs.getInt("id"));
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean add(Connection connection, UserInfo user) {
        if (findByLogin(connection, user.getLogin()) != null){
            return false;
        }
        PreparedStatement statement;
        StringBuilder createSql = new StringBuilder();
        try {
            createSql.append(String.format("INSERT INTO %s (login, password) VALUES ('%s', '%s')", this.name, user.getLogin(), Encryptor.encryptPassword(user.getPassword())));
            statement = connection.prepareStatement(createSql.toString());
            statement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
