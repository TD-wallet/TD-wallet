package td.wallet.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import td.wallet.models.Balance;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BalanceCrudOperationsTest implements CrudOperationsTest{

    private BalanceCrudOperations balanceCrudOperations;

    @BeforeEach
    public void setBalanceCrudOperations(){
        balanceCrudOperations = new BalanceCrudOperations();
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
        Balance balanceSaved = balanceCrudOperations.save(balanceToSave,2);
        assertNotNull(balanceSaved);
        assertNotEquals(0,balanceSaved.getId());
    }

    @Test
    @Override
    public void testSaveAll() {
        Balance balance1 = new Balance(5000.0);
        Balance balance2 = new Balance(3000.0);
        List<Balance> balanceList = Arrays.asList(balance1,balance2);
        List<Balance> balanceSaved = balanceCrudOperations.saveAll(balanceList,Arrays.asList(3,3));
        assertNotNull(balanceSaved);
        assertEquals(balanceList.size(),balanceSaved.size());
    }

    @Test
    @Override
    public void testDelete() {
        Balance balance = new Balance();
        Balance balanceToDelete = balanceCrudOperations.delete(balance);
        assertNull(balanceToDelete);
    }
}
