package td.wallet.controller;

import org.springframework.web.bind.annotation.*;
import td.wallet.models.Account;
import td.wallet.models.Category;
import td.wallet.models.Transfer;
import td.wallet.repository.utils.AccountTransferRole;
import td.wallet.service.CategoryService;
import td.wallet.service.TransferService;

import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    private final TransferService transferService;
    private final CategoryService categoryService;

    public TransferController(TransferService transferService, CategoryService categoryService) {
        this.transferService = transferService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public Transfer getTransferById(@PathVariable long id) {
        return transferService.findSingleTransferById(id);
    }

    @GetMapping("/all")
    public List<Transfer> getAllTransfer() {
        return transferService.findAllTransfer();
    }

    @GetMapping("/account")
    public List<Transfer> getTransferByAccount(@RequestParam Account account, @RequestParam String accountTransferRole) {
        return transferService.findTransferByAccount(account, AccountTransferRole.valueOf(accountTransferRole));
    }

    @PostMapping("/new")
    public Transfer postNewTransfer(@RequestBody Transfer transfer) {
        return transferService.saveSingleTransfer(transfer);
    }

    @PostMapping("/news")
    public List<Transfer> postNewTransfers(@RequestBody List<Transfer> transfers) {
        return transferService.saveMultipleTransfer(transfers);
    }

    @PostMapping
    public Transfer postTransferBetweenTwoAccounts(@RequestParam Account account1, @RequestParam Account account2, @RequestParam double amount, @RequestParam long categoryId) {
        Category category = categoryService.findCategoryById(categoryId);
        return transferService.transfer(account1, account2, amount, category);
    }
}
