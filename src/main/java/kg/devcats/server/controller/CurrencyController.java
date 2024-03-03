package kg.devcats.server.controller;

import jakarta.validation.Valid;
import kg.devcats.server.dto.request.SetSoftcoinCurrencyRequest;
import kg.devcats.server.dto.response.CurrencyResponse;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.service.CurrencyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8819", "http://194.152.37.7:8819"})
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyController {

    CurrencyService currencyService;

    @GetMapping
    public List<CurrencyResponse> fetchCurrencyValues(
            @RequestParam(value = "isoCodes", required = false) List<String> isoCodes) {
        return isoCodes == null ? currencyService.getCurrencies() : currencyService.getCurrenciesByCodes(isoCodes);
    }

    @PutMapping("/softcoin")
    public MessageResponse setSoftcoinCurrency(@Valid @RequestBody SetSoftcoinCurrencyRequest setSoftcoinCurrencyRequest) {
        return currencyService.setSoftcoinCurrency(setSoftcoinCurrencyRequest);
    }

    @GetMapping("/{isoCode}")
    public CurrencyResponse getCurrencyResponseByIsoCode(@PathVariable("isoCode") String isoCode) {
        return currencyService.getCurrencyResponseByIsoCode(isoCode.toUpperCase());
    }

}
