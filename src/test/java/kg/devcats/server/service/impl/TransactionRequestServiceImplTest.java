package kg.devcats.server.service.impl;

import kg.devcats.server.dto.response.TransactionRequestResponse;
import kg.devcats.server.entity.ClientAccount;
import kg.devcats.server.entity.TransactionRequest;
import kg.devcats.server.entity.User;
import kg.devcats.server.entity.enums.Role;
import kg.devcats.server.entity.enums.TransactionRequestStatus;
import kg.devcats.server.exception.RequestNotFoundException;
import kg.devcats.server.repo.TransactionRequestRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.Remove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TransactionRequestServiceImplTest {


    @Mock
    TransactionRequestRepository transactionRequestRepository;

    @InjectMocks
    TransactionRequestServiceImpl transactionRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPending() {
        TransactionRequestResponse response1 =
                new TransactionRequestResponse(1L,"user1@gmail.com","user2@gmail.com",
                        new BigDecimal(1000),Timestamp.valueOf(LocalDateTime.now()));
        TransactionRequestResponse response2 =
                new TransactionRequestResponse(2L,"user2@gmail.com","user1@gmail.com",
                        new BigDecimal(1200),Timestamp.valueOf(LocalDateTime.now()));
        List<TransactionRequestResponse> actualList = List.of(response1, response2);
        when(transactionRequestRepository.getAllPending()).thenReturn(actualList);

        List<TransactionRequestResponse> transactionRequestResponseList = transactionRequestService.getAllPending();

        verify(transactionRequestRepository,times(1)).getAllPending();
        assertIterableEquals(transactionRequestResponseList,actualList);
        assertEquals(2,transactionRequestResponseList.size());

    }

    @Test
    void save() {

        User user1 = User.builder()
                .email("user1")
                .password("popzvezda")
                .activated(true)
                .role(Role.ROLE_USER)
                .phoneNumber("+996509091625")
                .profileImagePath("smth")
                .deleted(false)
                .build();
        ClientAccount clientAccount1 = ClientAccount.builder()
                .balance(BigDecimal.valueOf(10000))
                .user(user1)
                .build();

        User user2 = User.builder()
                .email("user2")
                .password("popzvezda")
                .activated(true)
                .role(Role.ROLE_USER)
                .phoneNumber("+996509091625")
                .profileImagePath("smth")
                .deleted(false)
                .build();
        ClientAccount clientAccount2 = ClientAccount.builder()
                .balance(BigDecimal.valueOf(10000))
                .user(user2)
                .build();
        TransactionRequest request =
                new TransactionRequest(clientAccount1,clientAccount2,new BigDecimal(1000), TransactionRequestStatus.PENDING);

        when(transactionRequestRepository.save(any())).thenReturn(request);

        transactionRequestService.save(request);

        verify(transactionRequestRepository,times(1)).save(request);

    }

    @Test
    void getById() {
        User user1 = User.builder()
                .email("user1")
                .password("popzvezda")
                .activated(true)
                .role(Role.ROLE_USER)
                .phoneNumber("+996509091625")
                .profileImagePath("smth")
                .deleted(false)
                .build();
        ClientAccount clientAccount1 = ClientAccount.builder()
                .balance(BigDecimal.valueOf(10000))
                .user(user1)
                .build();

        User user2 = User.builder()
                .email("user2")
                .password("popzvezda")
                .activated(true)
                .role(Role.ROLE_USER)
                .phoneNumber("+996509091625")
                .profileImagePath("smth")
                .deleted(false)
                .build();
        ClientAccount clientAccount2 = ClientAccount.builder()
                .balance(BigDecimal.valueOf(10000))
                .user(user2)
                .build();
        TransactionRequest request =
                TransactionRequest.builder()
                                .sender(clientAccount1)
                                .receiver(clientAccount2)
                                .amount(new BigDecimal(1000))
                                .status(TransactionRequestStatus.PENDING)
                                .build();
        when(transactionRequestRepository.findPendingById(1L)).thenReturn(Optional.of(request));

        TransactionRequest byId = transactionRequestService.getById(1L);

        assertEquals(byId,request);
        verify(transactionRequestRepository,times(1)).findPendingById(1L);
        assertThrows(RequestNotFoundException.class, () -> {
            transactionRequestService.getById(2L);
        });

    }
}