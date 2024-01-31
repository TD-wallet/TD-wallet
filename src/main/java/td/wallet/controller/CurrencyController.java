package td.wallet.controller;

import org.springframework.web.bind.annotation.*;
import td.wallet.models.Currency;
import td.wallet.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
    @GetMapping("/{id}")
    public Currency getCurrencyById(@PathVariable int id) {
        return currencyService.findCurrencyById(id);
    }
    @GetMapping("/all")
    public List<Currency> getAllCurrencies() {
        return currencyService.findAllCurrencies();
    }
    @PostMapping("/new")
    public Currency postNewCurrency(@RequestBody Currency currency) {
        return currencyService.saveSingleCurrency(currency);
    }
    @PostMapping("/news")
    public List<Currency> postNewCurrencies(@RequestBody List<Currency> currencies) {
        return currencyService.saveMultipleCurrencies(currencies);
    }

    @DeleteMapping("/delete/{id}")
    public Currency deleteCurrency(@PathVariable int id) {
        return currencyService.deleteCurrency(id);
    }
}
