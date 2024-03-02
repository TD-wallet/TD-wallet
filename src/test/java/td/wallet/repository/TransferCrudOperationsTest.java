package td.wallet.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import td.wallet.models.Account;
import td.wallet.models.Transfer;
import td.wallet.repository.utils.AccountTransferRole;
import td.wallet.utils.ConnectionProvider;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransferCrudOperationsTest implements CrudOperationsTest {

    private final TransferCrudOperations transferCrudOperations;

    public TransferCrudOperationsTest(TransferCrudOperations transferCrudOperations) {
        this.transferCrudOperations = transferCrudOperations;
    }

    @BeforeAll
    public static void setOriginalConnection() {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        Connection connection = connectionProvider.getConnection();
    }



    @Test
    @Override
    public void testFindById() {
        Transfer transfer = transferCrudOperations.findById(1);
        assertNotNull(transfer);
    }

    @Test
    @Override
    public void testFindAll() {
        List<Transfer> transferList = transferCrudOperations.findAll();
        assertNotNull(transferList);
        assertFalse(transferList.isEmpty());
    }

    @Test
    @Override
    public void testSave() {
        Account account1 = new Account(5, "AZX444");
        Account account2 = new Account(6, "BTW326");
        Transfer toSave = new Transfer(account1, account2, 300.0, null);
        Transfer saving = transferCrudOperations.save(toSave);
        assertNotNull(saving);
    }

    @Test
    @Override
    public void testSaveAll() {
        Account account1 = new Account(5, "AZX444");
        Account account2 = new Account(6, "BTW326");

        Account account3 = new Account(3, "DEF456");
        Account account4 = new Account(7, "BJR666");
        Transfer transfer1 = new Transfer(account1, account2, 250.0, null);
        Transfer transfer2 = new Transfer(account3, account4, 200.0, null);
        List<Transfer> transferList = Arrays.asList(transfer1, transfer2);
        List<Transfer> toSave = transferCrudOperations.saveAll(transferList);
        assertNotNull(toSave);
        assertEquals(transferList.size(), toSave.size());

    }

    @Test
    @Override
    public void testDelete() {
        Transfer toDelete = transferCrudOperations.findById(2);
        Transfer deleted = transferCrudOperations.delete(toDelete);
        assertNull(deleted);
    }

    @Test
    public void testFindByAccount() {
        Account account = new Account(3, "DEF456");
        List<Transfer> transferList1 = transferCrudOperations.findByAccount(account, AccountTransferRole.CREDITED);
        List<Transfer> transferList2 = transferCrudOperations.findByAccount(account, AccountTransferRole.DEBITED);
        assertNotNull(transferList1);
        assertNotNull(transferList2);
        assertNotEquals(transferList1, transferList2);
    }
}
