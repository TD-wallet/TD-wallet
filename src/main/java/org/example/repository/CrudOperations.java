package org.example.repository;

import java.util.List;

public interface CrudOperations<T> {
    T findById(Integer id);

    List<T> findAll();

    List<T> saveAll(List<T> toSave);

    T save(T toSave);

    T delete(T toDelete);
}
