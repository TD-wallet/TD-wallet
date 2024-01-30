package td.wallet.repository;

import java.io.Serializable;
import java.util.List;

public interface CrudOperations<T> extends Serializable {
    T findById(long id);

    List<T> findAll();

    List<T> saveAll(List<T> toSave, List<Integer> relId);

    T save(T toSave, long relId);

    T delete(T toDelete);
}
