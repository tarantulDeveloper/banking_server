package kg.devcats.server.mapper;


import kg.devcats.server.dto.request.SetSoftcoinCurrencyRequest;
import kg.devcats.server.dto.response.CurrencyResponse;
import kg.devcats.server.entity.Currency;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyResponse toCurrencyResponse(Currency currency);

    List<CurrencyResponse> toCurrencyResponseList(List<Currency> currencies);
}
