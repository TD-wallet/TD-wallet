package td.wallet.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import td.wallet.models.Balance;
import td.wallet.utils.ConnectionProvider;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BalanceCrudOperationsTest implements CrudOperationsTest {

    private final BalanceCrudOperations balanceCrudOperations;

    public BalanceCrudOperationsTest(BalanceCrudOperations balanceCrudOperations) {
        this.balanceCrudOperations = balanceCrudOperations;
    }

    @BeforeAll
    public static void setOriginalConnection() {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        Connection connection = connectionProvider.getConnection();
    }


    @Test
    @Override
    public void testFindById() {
        Balance existingBalance = balanceCrudOperations.findById(1);

        assertNotNull(existingBalance);
    }

    @Test
    @Override
    public void testFindAll() {
        List<Balance> balanceList = balanceCrudOperations.findAll();
        assertNotNull(balanceList);
        assertFalse(balanceList.isEmpty());
    }

    @Test
    @Override
    public void testSave() {
        Balance balanceToSave = new Balance(6000.0);
        Balance balanceSaved = balanceCrudOperations.save(balanceToSave, 2);
        assertNotNull(balanceSaved);
        assertNotEquals(0, balanceSaved.getId());
    }

    @Test
    @Override
    public void testSaveAll() {
        Balance balance1 = new Balance(5000.0);
        Balance balance2 = new Balance(3000.0);
        List<Balance> balanceList = Arrays.asList(balance1, balance2);
        List<Balance> balanceSaved = balanceCrudOperations.saveAll(balanceList, Arrays.asList(3, 3));
        assertNotNull(balanceSaved);
        assertEquals(balanceList.size(), balanceSaved.size());
    }

    @Test
    @Override
    public void testDelete() {
        Balance balance = new Balance();
        Balance balanceToDelete = balanceCrudOperations.delete(balance);
        assertNull(balanceToDelete);
    }

    @Test
    public void testFindByAccountId() {
        List<Balance> balanceList = balanceCrudOperations.findByAccountId(3);
        assertNotNull(balanceList);
    }
}
