package kg.devcats.server.mapper;


import kg.devcats.server.dto.request.RegistrationRequestForDealer;
import kg.devcats.server.dto.response.RegistrationRequestResponse;
import kg.devcats.server.entity.Document;
import kg.devcats.server.entity.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public abstract class RegistrationRequestMapper {

    @Mapping(target = "password", ignore = true)
    public abstract RegistrationRequest toEntityRegistrationRequest(RegistrationRequestForDealer request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    public abstract Document registrationRequestToDocument(RegistrationRequest registrationRequest);

    public abstract List<RegistrationRequestResponse> productsToProductDTOs(List<RegistrationRequest> products);
}


