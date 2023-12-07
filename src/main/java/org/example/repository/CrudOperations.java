package org.example.repository;

import java.util.List;

public interface CrudOperations<T> {
    T findById(Integer id);

    List<T> findAll();

    List<T> saveAll(List<T> toSave, List<Integer> relId);

    T save(T toSave, int relId);

    T delete(T toDelete);
}
