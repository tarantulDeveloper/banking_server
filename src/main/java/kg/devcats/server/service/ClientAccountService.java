package kg.devcats.server.service;

import kg.devcats.server.entity.ClientAccount;

public interface ClientAccountService {
    ClientAccount findClientAccountByUserEmail(String email);

    void save(ClientAccount clientAccount);
}
