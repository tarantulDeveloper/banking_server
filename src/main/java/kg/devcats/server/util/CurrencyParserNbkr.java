package kg.devcats.server.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
@Slf4j
public class CurrencyParserNbkr {

    @Value("${exchange.daily-url}")
    private String dailyExchange;

    @Value("${exchange.weekly-url}")
    private String weeklyExchangeUrl;

    public Optional<Map<String, BigDecimal>> parse() {
        Map<String, BigDecimal> currencyValues = new HashMap<>();
        try {
            parseWeeklyExchange(currencyValues);
            parseDailyExchange(currencyValues);
            return Optional.of(currencyValues);
        } catch (IOException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    private void parseWeeklyExchange(Map<String, BigDecimal> currencyValues) throws IOException {
        Document doc = Jsoup.connect(weeklyExchangeUrl).get();
        parseCurrencyData(doc, currencyValues, 2);
    }

    private void parseDailyExchange(Map<String, BigDecimal> currencyValues) throws IOException {
        Document doc = Jsoup.connect(dailyExchange).get();
        parseCurrencyData(doc, currencyValues, 1);
    }

    private void parseCurrencyData(Document doc, Map<String, BigDecimal> currencyValues, int childIndex) {
        for (Element currency : doc.select("Currency")) {
            String isoCode = currency.attr("ISOCode");
            currencyValues.put(isoCode, new BigDecimal(currency.child(childIndex).ownText().replace(",", ".")));
        }
    }


}
