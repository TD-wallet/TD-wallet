package td.wallet.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import td.wallet.models.Currency;
import td.wallet.models.CurrencyValue;
import td.wallet.utils.ConnectionProvider;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyValueCrudOperationsTest implements CrudOperationsTest {

    public CurrencyValueCrudOperationsTest(CurrencyValueCrudOperations currencyValueCrudOperations) {
        this.currencyValueCrudOperations = currencyValueCrudOperations;
    }

    private final CurrencyValueCrudOperations currencyValueCrudOperations;

    @BeforeAll
    public static void setOriginalConnection() {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        Connection connection = connectionProvider.getConnection();
    }


    @AfterEach
    public void cleanUpTestSave() {
        cleanUpTestData();
    }

    @Test
    @Override
    public void testFindById() {
        CurrencyValue currencyValue = currencyValueCrudOperations.findById(1);
        assertNotNull(currencyValue);

    }

    @Test
    @Override
    public void testFindAll() {
        List<CurrencyValue> currencyValueList = currencyValueCrudOperations.findAll();
        assertNotNull(currencyValueList);
        assertFalse(currencyValueList.isEmpty());
    }

    @Test
    @Override
    public void testSave() {
        Currency currency1 = new Currency(2, null, null, null);
        Currency currency2 = new Currency(4, null, null, null);
        CurrencyValue currencyValue = new CurrencyValue(currency1, currency2, 100.0, null);
        CurrencyValue toSave = currencyValueCrudOperations.save(currencyValue);
        assertNotNull(toSave);
    }

    @Test
    @Override
    public void testSaveAll() {
        Currency currency1 = new Currency(2, null, null, null);
        Currency currency2 = new Currency(4, null, null, null);
        CurrencyValue currencyValue1 = new CurrencyValue(currency1, currency2, 150.0, null);
        CurrencyValue currencyValue2 = new CurrencyValue(currency1, currency2, 90.0, null);
        List<CurrencyValue> currencyValueList = Arrays.asList(currencyValue1, currencyValue2);
        List<CurrencyValue> toSave = currencyValueCrudOperations.saveAll(currencyValueList);
        assertNotNull(toSave);
        assertEquals(currencyValueList.size(), toSave.size());
    }

    @Test
    @Override
    public void testDelete() {
        CurrencyValue currencyValue = currencyValueCrudOperations.findById(3);
        CurrencyValue toDelete = currencyValueCrudOperations.delete(currencyValue);
        assertNotNull(toDelete);
        assertEquals(currencyValue.getId(), toDelete.getId());
    }

    private void cleanUpTestData() {
        CurrencyValue currencyValue1 = currencyValueCrudOperations.findById(4);
        CurrencyValue currencyValue2 = currencyValueCrudOperations.findById(5);
        CurrencyValue currencyValue3 = currencyValueCrudOperations.findById(6);

        if (currencyValue1 != null) {
            currencyValueCrudOperations.delete(currencyValue1);
        }
    }
}
