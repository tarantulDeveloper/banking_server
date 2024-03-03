package kg.devcats.server.controller;

import kg.devcats.server.dto.response.HistoryResponse;
import kg.devcats.server.endpoint.HistoryEndpoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8819", "http://194.152.37.7:8819"})
@RequestMapping("/api/history")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryController {
    HistoryEndpoint historyEndpoint;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<HistoryResponse> getHistoryForPurchases(@AuthenticationPrincipal UserDetails userDetail) {
        return historyEndpoint.getAllPurchases(userDetail.getUsername());
    }

}
