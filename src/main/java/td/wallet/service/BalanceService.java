package td.wallet.service;

import org.springframework.stereotype.Service;
import td.wallet.models.Balance;
import td.wallet.repository.BalanceCrudOperations;

import java.util.List;

@Service
public class BalanceService {
    private final BalanceCrudOperations balanceCrudOperations;

    public BalanceService(BalanceCrudOperations balanceCrudOperations) {
        this.balanceCrudOperations = balanceCrudOperations;
    }

    public Balance findBalanceById(long id) {
        return balanceCrudOperations.findById(id);
    }

    public List<Balance> findBalanceByAccountId(long accountId) {
        return balanceCrudOperations.findByAccountId(accountId);
    }

    public List<Balance> findAllBalances() {
        return balanceCrudOperations.findAll();
    }

    public Balance saveSingleBalance(Balance balance, long relId) {
        return balanceCrudOperations.save(balance, relId);
    }

    public List<Balance> saveMultipleBalances(List<Balance> balances, List<Integer> relId) {
        return balanceCrudOperations.saveAll(balances,relId);
    }

}
