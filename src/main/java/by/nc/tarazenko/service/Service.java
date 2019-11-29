package by.nc.tarazenko.service;

import by.nc.tarazenko.entity.Guest;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    Optional <T> getById(int id);

    List<Guest> getAll();

    void create(T entity);

    Optional<T> update(T entity);

    void delete(T entity);

    void deleteById(int entityId);
}


