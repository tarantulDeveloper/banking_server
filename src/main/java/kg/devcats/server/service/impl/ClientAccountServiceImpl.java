package kg.devcats.server.service.impl;


import kg.devcats.server.entity.ClientAccount;
import kg.devcats.server.exception.ClientAccountNotFoundException;
import kg.devcats.server.repo.ClientAccountRepository;
import kg.devcats.server.service.ClientAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientAccountServiceImpl implements ClientAccountService {

    ClientAccountRepository clientAccountRepository;

    @Override
    public ClientAccount findClientAccountByUserEmail(String email) {
        return clientAccountRepository.findClientAccountByUserEmail(email).orElseThrow(
                () -> new ClientAccountNotFoundException("Client has not been found")
        );
    }

    @Override
    public void save(ClientAccount clientAccount) {
        clientAccountRepository.save(clientAccount);
    }
}
