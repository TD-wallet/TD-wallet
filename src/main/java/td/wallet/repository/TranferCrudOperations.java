package td.wallet.repository;

import td.wallet.models.Transfer;

import java.util.List;

public class TranferCrudOperations implements CrudOperations<Transfer> {

    @Override
    public Transfer findById(Integer id) {
        return null;
    }

    @Override
    public List<Transfer> findAll() {
        return null;
    }

    @Override
    public List<Transfer> saveAll(List<Transfer> toSave, List<Integer> relId) {
        return null;
    }

    @Override
    public Transfer save(Transfer toSave, int relId) {
        return null;
    }

    @Override
    public Transfer delete(Transfer toDelete) {
        return null;
    }
}
