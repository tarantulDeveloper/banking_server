package kg.devcats.server.service.impl;

import kg.devcats.server.dto.response.UserPersonalAccountResponse;
import kg.devcats.server.entity.ClientAccount;
import kg.devcats.server.entity.User;
import kg.devcats.server.exception.UserNotFoundException;
import kg.devcats.server.repo.ClientAccountRepository;
import kg.devcats.server.repo.UserRepository;
import kg.devcats.server.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    ClientAccountRepository clientAccountRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    @Override
    public UserPersonalAccountResponse findByEmail(String email) {
        return userRepository.findPersonalInfoByEmail(email);

    }

    @Override
    public Optional<User> findByActivationToken(String token) {
        return userRepository.findByActivationToken(token);
    }

    @Override
    public BigDecimal getPersonalBalance(Long userId) {
        ClientAccount clientAccount = clientAccountRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        return clientAccount.getBalance();
    }
}
