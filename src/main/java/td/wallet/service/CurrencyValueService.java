package td.wallet.service;

import org.springframework.stereotype.Service;
import td.wallet.models.Currency;
import td.wallet.models.CurrencyValue;
import td.wallet.repository.CurrencyValueCrudOperations;
import td.wallet.repository.utils.ValueType;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CurrencyValueService {
    private final CurrencyValueCrudOperations currencyValueCrudOperations;

    public CurrencyValueService(CurrencyValueCrudOperations currencyValueCrudOperations) {
        this.currencyValueCrudOperations = currencyValueCrudOperations;
    }

    public CurrencyValue findCurrencyValueById(long id) {
        return currencyValueCrudOperations.findById(id);
    }

    public List<CurrencyValue> findAllCurrencyValue() {
        return currencyValueCrudOperations.findAll();
    }

    public CurrencyValue findCurrentValue(Currency source, Currency destination) {
        return currencyValueCrudOperations.findCurrentValue(source, destination);
    }

    public CurrencyValue findValueWithChoiceOfTheType(Currency source, Currency destination, Timestamp date, ValueType valueType) {
        return currencyValueCrudOperations.findValue(source, destination, date, valueType);
    }

    public CurrencyValue saveSingleCurrency(CurrencyValue value) {
        return currencyValueCrudOperations.save(value);
    }

    public List<CurrencyValue> saveMultipleCurrency(List<CurrencyValue> currencyValues) {
        return currencyValueCrudOperations.saveAll(currencyValues);
    }

    public CurrencyValue deleteCurrencyValue(long id) {
        CurrencyValue toDelete = currencyValueCrudOperations.findById(id);
        return currencyValueCrudOperations.delete(toDelete);
    }
}
