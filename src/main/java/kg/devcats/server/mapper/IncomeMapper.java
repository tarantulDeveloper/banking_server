package kg.devcats.server.mapper;

import kg.devcats.server.dto.response.IncomeResponseForAdmin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface IncomeMapper {

    @Mapping(source = "incomeAmount", target = "incomeAmount")
    @Mapping(source = "systemPortion", target = "systemPortion")
    @Mapping(source = "dealerPortion", target = "dealerPortion")
    @Mapping(source = "cost", target = "cost")
    @Mapping(source = "realCost", target = "realCost")
    IncomeResponseForAdmin toIncomeResponseForAdminInCurrency(IncomeResponseForAdmin incomeResponseForAdmin,
                                                              BigDecimal incomeAmount, BigDecimal systemPortion,
                                                              BigDecimal dealerPortion, BigDecimal cost, BigDecimal realCost);

}
