package td.wallet.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import td.wallet.models.Category;
import td.wallet.models.Transaction;
import td.wallet.models.TransactionType;
import td.wallet.utils.ConnectionProvider;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionCrudOperationsTest implements CrudOperationsTest {

    private TransactionCrudOperations transactionCrudOperations;

    @BeforeAll
    public static void setOriginalConnection() {
        Connection originalConnection = ConnectionProvider.getConnection();
    }

    @BeforeEach
    public void setTransactionCrudOperations() {
        transactionCrudOperations = new TransactionCrudOperations();
    }

    @AfterEach
    public void cleanUpTestSave() {
        cleanUpTestData();
    }

    @Test
    @Override
    public void testFindById() {
        Transaction transaction = transactionCrudOperations.findById(2);
        assertNotNull(transaction);
    }

    @Test
    @Override
    public void testFindAll() {
        List<Transaction> transactionList = transactionCrudOperations.findAll();
        assertNotNull(transactionList);
        assertFalse(transactionList.isEmpty());
    }

    @Test
    @Override
    public void testSave() {
        Category category = new Category(4, null, null);
        Transaction transaction = new Transaction(2000.0, "blabla", category, TransactionType.CREDIT);
        Transaction toSave = transactionCrudOperations.save(transaction, 5);
        assertNotNull(toSave);
    }

    @Test
    @Override
    public void testSaveAll() {
        Category category1 = new Category(4, null, null);
        Category category2 = new Category(20, null, null);
        Transaction transaction1 = new Transaction(200.0, "test1", category1, TransactionType.CREDIT);
        Transaction transaction2 = new Transaction(200.0, "test2", category2, TransactionType.DEBIT);
        List<Transaction> transactionList = Arrays.asList(transaction1, transaction2);
        List<Integer> accountId = Arrays.asList(6, 7);
        List<Transaction> listToSave = transactionCrudOperations.saveAll(transactionList, accountId);
        assertNotNull(listToSave);
        assertEquals(transactionList.size(), listToSave.size());
    }

    @Test
    @Override
    public void testDelete() {
        Transaction transaction = transactionCrudOperations.findById(3);
        Transaction toDelete = transactionCrudOperations.delete(transaction);
        assertNotNull(toDelete);
        assertEquals(transaction.getId(), toDelete.getId());
    }

    private void cleanUpTestData() {
        Transaction transaction1 = transactionCrudOperations.findById(4);
        Transaction transaction2 = transactionCrudOperations.findById(5);
        Transaction transaction3 = transactionCrudOperations.findById(6);

        if (transaction1 != null) {
            transactionCrudOperations.delete(transaction1);
        }
        if (transaction2 != null) {
            transactionCrudOperations.delete(transaction2);
        }
        if (transaction3 != null) {
            transactionCrudOperations.delete(transaction3);
        }
    }
}
