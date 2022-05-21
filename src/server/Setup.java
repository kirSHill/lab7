package server;

import client.Client;
import essentials.interaction.UserInteraction;
import server.models.CityModel;
import server.models.Model;
import server.models.UserModel;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;

public abstract class Setup {

    private static Connection connection;
    private static final Logger log = Logger.getLogger(Client.class.getName());

    public static Connection getConnection() {
        return connection;
    }

    public static boolean createConnection(UserInteraction interaction, File file) {
        Connection c;
        try {
            Class.forName("org.postgresql.Driver");
            Properties info = new Properties();
            info.load(new FileInputStream(file));
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info.getProperty("DB_LOGIN"), info.getProperty("DB_PASSWORD"));
            log.info("Успешное соединение с базой данных.\n");
            connection = c;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Ошибка при соединении с базой данных.\n");
            return false;
        }
    }

    public static void createTables(UserInteraction interaction) {
        Model[] models = {
                new UserModel(),
                new CityModel()
        };
        for (Model model : models) {
            if (model.createTable(connection)) {
                log.info(String.format("Таблица %s создана!\n", model.getName()));
            } else {
                log.info(String.format("Таблица %s уже существует!\n", model.getName()));
            }
        }
        log.info("Преднастройка базы данных завершена!\n");
    }
}
