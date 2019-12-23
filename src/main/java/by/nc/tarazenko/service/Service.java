package by.nc.tarazenko.service;

import java.util.List;

public interface Service<T> {
    T getById(int id);

    List<T> getAll();

    T create(T entity);

    T update(T entity);

    void deleteById(int entityId);
}


