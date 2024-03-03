package kg.devcats.server.dto.response;


public record MonthlyPurchaseResponseForManager(
        String month,
        Long total
){
}
