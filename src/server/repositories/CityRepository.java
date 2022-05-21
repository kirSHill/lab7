package server.repositories;

import essentials.elements.City;
import server.models.CityModel;

import java.sql.Connection;
import java.util.PriorityQueue;

public class CityRepository implements Repository<City> {

    private final CityModel cityModel = new CityModel();
    private final Connection connection;

    public CityRepository(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean save(City obj) {
        return cityModel.add(connection, obj);
    }

    public City saveGet(City obj) {
        return cityModel.addGet(connection, obj);
    }

    @Override
    public PriorityQueue<City> getAll() {
        return cityModel.findAll(connection);
    }

    @Override
    public City getById(int id) {
        return cityModel.findById(connection,id);
    }

    @Override
    public boolean deleteById(int id) {
        return cityModel.delete(id, connection);
    }

    @Override
    public boolean updateById(int id, City city) {
        return cityModel.updateById(id, city, connection);
    }

    public int getMaxId(){
        return cityModel.findMaxId(connection);
    }
}
