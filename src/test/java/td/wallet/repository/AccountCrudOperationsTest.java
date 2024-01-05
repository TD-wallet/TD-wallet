package td.wallet.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import td.wallet.models.Account;
import td.wallet.models.Currency;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountCrudOperationsTest implements CrudOperationsTest{
    private AccountCrudOperations accountCrudOperations;

    @BeforeEach
    public void setAccountCrudOperations(){
        accountCrudOperations = new AccountCrudOperations();
    }
    @Test
    @Override
    public void testFindById() {
        Account account = accountCrudOperations.findById(1);
        assertNotNull(account);
    }

    @Test
    @Override
    public void testFindAll() {
        List<Account> accountList = accountCrudOperations.findAll();
        assertNotNull(accountList);
        assertFalse(accountList.isEmpty());
    }
    @Test
    @Override
    public void testSave() {
        Currency currency = new Currency(1,"US Dollar","USD","$");
        Account accountToSave = new Account("AZX444",currency);
        Account toSave = accountCrudOperations.save(accountToSave,2);
        assertNotNull(toSave);
    }

    @Test
    @Override
    public void testSaveAll() {
        Currency currency = new Currency(1,"US Dollar","USD","$");
        Account account1 = new Account("BTW326",currency);
        Account account2 = new Account("BJR666",currency);
        List<Account> accountList = Arrays.asList(account1,account2);
        List<Integer> relId = Arrays.asList(1,3);
        List<Account> toSave = accountCrudOperations.saveAll(accountList,relId);
        assertNotNull(toSave);
        assertEquals(accountList.size(),toSave.size());
    }

    @Test
    @Override
    public void testDelete() {
        Account toDelete = accountCrudOperations.findById(4);
        Account deletedAccount = accountCrudOperations.delete(toDelete);
        assertNotNull(deletedAccount);
        assertEquals(toDelete.getId(),deletedAccount.getId());
    }
}
