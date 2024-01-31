package td.wallet.service;

import org.springframework.stereotype.Service;
import td.wallet.models.Currency;
import td.wallet.repository.CurrencyCrudOperations;

import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyCrudOperations currencyCrudOperations;

    public CurrencyService(CurrencyCrudOperations currencyCrudOperations) {
        this.currencyCrudOperations = currencyCrudOperations;
    }

    public Currency findCurrencyById(int id) {
        return currencyCrudOperations.findById(id);
    }
    public List<Currency> findAllCurrencies() {
        return currencyCrudOperations.findAll();
    }

    public Currency saveSingleCurrency(Currency currency) {
        return currencyCrudOperations.save(currency);
    }

    public List<Currency> saveMultipleCurrencies(List<Currency> currencies) {
        return currencyCrudOperations.saveAll(currencies);
    }

    public Currency deleteCurrency(int id) {
        Currency toDelete = currencyCrudOperations.findById(id);
        return currencyCrudOperations.delete(toDelete);
    }
}
