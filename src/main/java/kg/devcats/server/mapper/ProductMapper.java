package kg.devcats.server.mapper;


import kg.devcats.server.dto.request.ProductCreateRequest;
import kg.devcats.server.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(target = "price",source = "price",qualifiedByName = "roundValue")
    @Mapping(target = "commission",source = "commission",qualifiedByName = "roundValue")
    Product toEntity(ProductCreateRequest productCreateRequest);


    @Named("roundValue")
    static BigDecimal roundPrice(BigDecimal previous) {
        return previous.setScale(0, RoundingMode.HALF_UP);
    }


}
