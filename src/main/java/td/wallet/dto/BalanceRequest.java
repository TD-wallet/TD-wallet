package td.wallet.dto;

import td.wallet.models.Balance;

import java.util.List;

public class BalanceRequest {
    private final List<Balance> balances;
    private final List<Integer> relId;

    public BalanceRequest(List<Balance> balances, List<Integer> relId) {
        this.balances = balances;
        this.relId = relId;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public List<Integer> getRelId() {
        return relId;
    }

}
