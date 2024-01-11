package td.wallet.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import td.wallet.models.Currency;
import td.wallet.utils.ConnectionProvider;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyCrudOperationsTest implements CrudOperationsTest {
    private CurrencyCrudOperations currencyCrudOperations;

    @BeforeAll
    public static void setOriginalConnection() {
        Connection originalConnection = ConnectionProvider.getConnection();
    }

    @BeforeEach
    public void setCurrencyCrudOperations() {
        currencyCrudOperations = new CurrencyCrudOperations();
    }

    @AfterEach
    public void cleanUpTestSave() {
        cleanUpTestData();
    }

    @Test
    @Override
    public void testFindById() {
        Currency currency = currencyCrudOperations.findById(2);
        assertNotNull(currency);
    }

    @Test
    @Override
    public void testFindAll() {
        List<Currency> currencyList = currencyCrudOperations.findAll();
        assertNotNull(currencyList);
        assertFalse(currencyList.isEmpty());
    }

    @Test
    @Override
    public void testSave() {
        Currency currency = new Currency("Ariary", "MGA", "Ar");
        Currency toSave = currencyCrudOperations.save(currency);
        assertNotNull(toSave);
    }

    @Test
    @Override
    public void testSaveAll() {
        Currency currency1 = new Currency("Taka", "BDT", "৳");
        Currency currency2 = new Currency("Ngultrum", "BTN", "Nu");
        List<Currency> currencyList = Arrays.asList(currency1, currency2);
        List<Currency> toSave = currencyCrudOperations.saveAll(currencyList);
        assertNotNull(toSave);
        assertEquals(currencyList.size(), toSave.size());
    }

    @Test
    @Override
    public void testDelete() {
        Currency currency = currencyCrudOperations.findById(5);
        Currency toDelete = currencyCrudOperations.delete(currency);
        assertNotNull(toDelete);
        assertEquals(currency.getId(), toDelete.getId());
    }

    private void cleanUpTestData() {
        Currency currency1 = currencyCrudOperations.findById(4);
        Currency currency2 = currencyCrudOperations.findById(5);
        Currency currency3 = currencyCrudOperations.findById(6);

        if (currency1 != null) {
            currencyCrudOperations.delete(currency1);
        }
        if (currency2 != null) {
            currencyCrudOperations.delete(currency2);
        }
        if (currency3 != null) {
            currencyCrudOperations.delete(currency3);
        }
    }
}
