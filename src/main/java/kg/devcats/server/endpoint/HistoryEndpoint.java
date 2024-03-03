package kg.devcats.server.endpoint;

import kg.devcats.server.dto.response.HistoryResponse;
import kg.devcats.server.entity.Purchase;
import kg.devcats.server.service.HistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryEndpoint {

    HistoryService historyService;


    public List<HistoryResponse> getAllPurchases(String email){
        List<Purchase> purchasesHistory = historyService.getPurchasesHistory(email);
        return purchasesHistory.stream().map(purchase ->
                new HistoryResponse(purchase.getDealer().getEmail(),purchase.getQuantity(),
                        purchase.getProduct().getName(),purchase.getCost(),purchase.getCreatedAt())).collect(Collectors.toList());
    }
}
