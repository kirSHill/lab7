package server.repositories;

import java.util.PriorityQueue;

public interface Repository<T> {

    boolean save(T obj);

    PriorityQueue<T> getAll();

    T getById(int id);

    boolean deleteById(int id);

    boolean updateById(int id, T obj);
}
