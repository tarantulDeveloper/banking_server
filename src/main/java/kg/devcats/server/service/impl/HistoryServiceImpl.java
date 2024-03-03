package kg.devcats.server.service.impl;

import kg.devcats.server.entity.Purchase;
import kg.devcats.server.repo.HistoryRepository;
import kg.devcats.server.service.HistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HistoryServiceImpl implements HistoryService {

    HistoryRepository historyRepository;

    @Override
    public List<Purchase> getPurchasesHistory(String email) {
        return historyRepository.findAllExistingByOwnerEmail(email);
    }
}
