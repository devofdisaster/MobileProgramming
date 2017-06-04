package com.example.piotr.lab5;

import java.util.List;

public interface DAO<T> {
    long save(T object);
    void update(T object);
    void delete(T object);
    T get(long id);
    List<T> getAll();
}
