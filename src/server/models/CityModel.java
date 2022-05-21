package server.models;

import essentials.elements.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.PriorityQueue;

public class CityModel extends Model {

    public CityModel() {
        this.name = "Cities";
        this.fields = Arrays.asList(new FieldDB("id", "SERIAL PRIMARY KEY"),
                new FieldDB("name", "varchar(256)"),
                new FieldDB("x", "int8"),
                new FieldDB("y", "int"),
                new FieldDB("area", "int"),
                new FieldDB("population", "int"),
                new FieldDB("meters_above_sea_level", "int"),
                new FieldDB("creation_date", "varchar(256)"),
                new FieldDB("capital","bool"),
                new FieldDB("name_governor", "varchar(256)"),
                new FieldDB("age_governor", "int8"),
                new FieldDB("height_governor", "float8"),
                new FieldDB("government", "varchar(256)"),
                new FieldDB("climate", "varchar(256)"),
                new FieldDB("client_id", "int"));
    }

    public City findById(Connection connection, int id) {
        City city = new City();
        Human governor = new Human();
        PreparedStatement statement;
        StringBuilder createSql = new StringBuilder();
        try {
            createSql.append(String.format("SELECT * FROM %s WHERE id='%s'", this.name, id));
            statement = connection.prepareStatement(createSql.toString());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                city.setClientId(rs.getInt("client_id"));
                city.setCoordinates(new Coordinates(rs.getLong("x"), rs.getInt("y")));
                city.setArea(rs.getInt("area"));
                city.setPopulation(rs.getInt("population"));
                city.setMetersAboveSeaLevel(rs.getInt("meters_above_sea_level"));
                city.setId(rs.getInt("id"));
                city.setName(rs.getString("name"));
                city.setCapital(rs.getBoolean("capital"));
                city.setClimate(Climate.getByName(rs.getString("climate")));
                city.setGovernment(Government.getByName(rs.getString("government")));
                city.setCreationDate(LocalDateTime.parse(rs.getString("creation_date")));
                governor.setNameG(rs.getString("name_governor"));
                governor.setAge(rs.getLong("age_governor"));
                governor.setHeight(rs.getDouble("height_governor"));
                city.setGovernor(governor);
            }
        } catch (Exception e) {
            return null;
        }
        return city;
    }

    public PriorityQueue<City> findAll(Connection connection) {
        PriorityQueue<City> collection = new PriorityQueue<>();
        PreparedStatement statement;
        StringBuilder createSql = new StringBuilder();
        try {
            createSql.append(String.format("SELECT * FROM %s", this.name));
            statement = connection.prepareStatement(createSql.toString());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                City city = new City();
                Human governor = new Human();
                city.setClientId(rs.getInt("client_id"));
                city.setCoordinates(new Coordinates(rs.getLong("x"), rs.getInt("y")));
                city.setArea(rs.getInt("area"));
                city.setPopulation(rs.getInt("population"));
                city.setMetersAboveSeaLevel(rs.getInt("meters_above_sea_level"));
                city.setId(rs.getInt("id"));
                city.setMaxId(rs.getInt("id"));
                city.setName(rs.getString("name"));
                city.setCapital(rs.getBoolean("capital"));
                city.setClimate(Climate.getByName(rs.getString("climate")));
                city.setGovernment(Government.getByName(rs.getString("government")));
                city.setCreationDate(LocalDateTime.parse(rs.getString("creation_date")));
                governor.setNameG(rs.getString("name_governor"));
                governor.setAge(rs.getLong("age_governor"));
                governor.setHeight(rs.getDouble("height_governor"));
                city.setGovernor(governor);
                collection.add(city);
            }
        } catch (Exception e) {
            System.out.println("Коллекция не может быть выведена.");
            return null;
        }
        return collection;
    }

    public boolean add(Connection connection, City city) {
        PreparedStatement statement;
        StringBuilder createSql = new StringBuilder();
        try {
            createSql.append(String.format("INSERT INTO %s (id, name, x, y, area, population, meters_above_sea_level, creation_date, capital, name_governor, age_governor, height_governor, climate, government, client_id) VALUES ",this.name));
            createSql.append(String.format("(%s, '%s', %s, %s, %s, %s, %s, '%s', '%s', '%s', %s, %s, '%s', '%s', %d)",
                    city.getId().toString(),
                    city.getName(),
                    city.getCoordinates().getX().toString(),
                    String.valueOf(city.getCoordinates().getY()).replace(',','.'),
                    city.getArea().toString(),
                    city.getPopulation().toString(),
                    city.getMetersAboveSeaLevel().toString(),
                    city.getCreationDate().toString(),
                    city.isCapital().toString(),
                    city.getGovernor().getNameG(),
                    city.getGovernor().getAge().toString(),
                    city.getGovernor().getHeight().toString(),
                    city.getClimate().toString(),
                    city.getGovernment().toString(),
                    city.getClientId()));
            statement = connection.prepareStatement(createSql.toString());
            statement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public City addGet(Connection connection, City city) {
        if (add(connection, city)) {
            PreparedStatement statement;
            StringBuilder createSql = new StringBuilder();
            try {
                createSql.append(String.format("SELECT * FROM %s ORDER BY id DESC LIMIT 1", this.name));
                statement = connection.prepareStatement(createSql.toString());
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    city.setId(rs.getInt("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return city;
        }
        return null;
    }

    public int findMaxId(Connection connection) {
        PreparedStatement statement;
        StringBuilder createSql = new StringBuilder();
        try {
            createSql.append(String.format("SELECT id FROM %s ORDER BY id DESC LIMIT 1", this.name));
            statement = connection.prepareStatement(createSql.toString());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    public boolean delete(int id, Connection connection) {
        PreparedStatement statement;
        StringBuilder createSql = new StringBuilder();
        try {
            createSql.append(String.format("DELETE FROM %s WHERE id = %d", this.name, id));
            statement = connection.prepareStatement(createSql.toString());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateById(int id, City city, Connection connection) {
        PreparedStatement statement;
        String[] s;
        try {
            s = new String[]{String.format("UPDATE %s SET name='%s' WHERE id=%d", this.name, city.getName(), id),
                    String.format("UPDATE %s SET area='%s' WHERE id=%d", this.name, city.getArea().toString(), id),
                    String.format("UPDATE %s SET population='%s' WHERE id=%d", this.name, city.getPopulation().toString(), id),
                    String.format("UPDATE %s SET meters_above_sea_level='%s' WHERE id=%d", this.name, city.getMetersAboveSeaLevel().toString(), id),
                    String.format("UPDATE %s SET climate='%s' WHERE id=%d", this.name, city.getClimate().toString(), id),
                    String.format("UPDATE %s SET creation_date='%s' WHERE id=%d", this.name, city.getCreationDate().toString(), id),
                    String.format("UPDATE %s SET y=%s WHERE id=%d", this.name, String.valueOf(city.getCoordinates().getY()).replace(',', '.'), city.getId()),
                    String.format("UPDATE %s SET x=%s WHERE id=%d", this.name, city.getCoordinates().getX(), id),
                    String.format("UPDATE %s SET capital=%s WHERE id=%d", this.name, city.isCapital().toString(), id),
                    String.format("UPDATE %s SET government=%s WHERE id=%d", this.name, city.getGovernment().toString(), id),
                    String.format("UPDATE %s SET name_governor=%s WHERE id=%d", this.name, city.getGovernor().getNameG(), id),
                    String.format("UPDATE %s SET age_governor=%s WHERE id=%d", this.name, city.getGovernor().getAge().toString(), id),
                    String.format("UPDATE %s SET height_governor=%s WHERE id=%d", this.name, city.getGovernor().getHeight().toString(), id),
            };
            for (String req : s) {
                statement = connection.prepareStatement(req);
                statement.execute();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

