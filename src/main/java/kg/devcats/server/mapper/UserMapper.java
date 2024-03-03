package kg.devcats.server.mapper;

import kg.devcats.server.dto.request.RegistrationRequestForDealer;
import kg.devcats.server.dto.response.AdminOrManagerInfoResponse;
import kg.devcats.server.dto.response.ClientFullInfoResponse;
import kg.devcats.server.dto.response.UserFullInfoResponse;
import kg.devcats.server.entity.Document;
import kg.devcats.server.entity.RegistrationRequest;
import kg.devcats.server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    User fromRegistrationRequestToUserEntity(RegistrationRequest registrationRequest);

    @Mapping(target = "password", ignore = true)
    User fromRegistrationRequestForDealerRequestToUserEntity(RegistrationRequestForDealer registrationRequest);

    Document fromRegistrationRequestForDealerRequestToDocumentEntity(RegistrationRequestForDealer request);

    UserFullInfoResponse toUserFullInfoResponse(User user);

    List<UserFullInfoResponse> toUserFullInfoResponse(List<User> users);

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.createdAt", target = "createdAt")
    @Mapping(source = "user.updatedAt", target = "updatedAt")
    @Mapping(source = "user.deleted", target = "deleted")
    ClientFullInfoResponse toClientInfoResponse(User user, Document document);

    AdminOrManagerInfoResponse toAdminOrManagerInfoResponse(User user);
}
