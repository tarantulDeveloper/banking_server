package kg.devcats.server.service;

import kg.devcats.server.dto.request.SetSoftcoinCurrencyRequest;
import kg.devcats.server.dto.response.CurrencyResponse;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.entity.Currency;

import java.util.List;

public interface CurrencyService {

    void updateCurrencyValuesTask();

    void initValues();

    List<CurrencyResponse> getCurrencies();

    List<CurrencyResponse> getCurrenciesByCodes(List<String> isoCodes);

    MessageResponse setSoftcoinCurrency(SetSoftcoinCurrencyRequest setSoftcoinCurrencyRequest);

    Currency findByIsoCode(String isoCode);

    CurrencyResponse getCurrencyResponseByIsoCode(String isoCode);
}
