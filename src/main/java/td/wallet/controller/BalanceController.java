package td.wallet.controller;

import org.springframework.web.bind.annotation.*;
import td.wallet.dto.BalanceRequest;
import td.wallet.models.Balance;
import td.wallet.service.BalanceService;

import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController {
    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/{id}")
    public Balance getBalanceById(@PathVariable long id) {
        return balanceService.findBalanceById(id);
    }

    @GetMapping("/{id}")
    public List<Balance> getListOfBalanceByAccountId(@PathVariable(name = "id") long accountId) {
        return balanceService.findBalanceByAccountId(accountId);
    }

    @GetMapping("/all")
    public List<Balance> getAllBalances() {
        return balanceService.findAllBalances();
    }

    @PostMapping("/new-balance")
    public Balance postNewBalance(@RequestBody Balance balance, @RequestParam(name = "id") long relId) {
        return balanceService.saveSingleBalance(balance, relId);
    }

    @PostMapping("/new-balances")
    public List<Balance> postNewBalances(@RequestBody BalanceRequest balanceRequest) {
        return balanceService.saveMultipleBalances(balanceRequest.getBalances(), balanceRequest.getRelId());
    }
}
