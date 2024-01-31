package td.wallet.controller;

import org.springframework.web.bind.annotation.*;
import td.wallet.models.Currency;
import td.wallet.models.CurrencyValue;
import td.wallet.repository.utils.ValueType;
import td.wallet.service.CurrencyValueService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/currency-value")
public class CurrencyValueController {
    private final CurrencyValueService currencyValueService;

    public CurrencyValueController(CurrencyValueService currencyValueService) {
        this.currencyValueService = currencyValueService;
    }

    @GetMapping("/{id}")
    public CurrencyValue getCurrencyValueById(@PathVariable long id) {
        return currencyValueService.findCurrencyValueById(id);
    }
    @GetMapping("/all")
    public List<CurrencyValue> getAllCurrencyValues() {
        return currencyValueService.findAllCurrencyValue();
    }
    @GetMapping("/current-value")
    public CurrencyValue getCurrentValue(@RequestParam Currency source, @RequestParam Currency destination) {
        return currencyValueService.findCurrentValue(source, destination);
    }
    @GetMapping("/with-type")
    public CurrencyValue getCurrencyValueWithChoiceOfTheType(@RequestParam Currency source, @RequestParam Currency destination, @RequestParam Timestamp date, @RequestParam ValueType type) {
        return currencyValueService.findValueWithChoiceOfTheType(source, destination, date, type);
    }

    @PostMapping("/new-currency-value")
    public CurrencyValue postNewCurrencyValue(@RequestBody CurrencyValue value) {
        return currencyValueService.saveSingleCurrency(value);
    }

    @PostMapping("/new-currency-values")
    public List<CurrencyValue> postNewCurrencyValues(@RequestBody List<CurrencyValue> values) {
        return currencyValueService.saveMultipleCurrency(values);
    }
    @DeleteMapping("/delete-currency/{id}")
    public CurrencyValue deleteCurrencyValue(@PathVariable long id) {
        return currencyValueService.deleteCurrencyValue(id);
    }

}
