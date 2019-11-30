package by.nc.tarazenko.service;

import by.nc.tarazenko.entity.Guest;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    T getById(int id);

    List<T> getAll();

    void create(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(int entityId);
}


