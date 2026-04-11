package stock1337.stock1337.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stock1337.stock1337.Mapper.StockHistoryMapper;
import stock1337.stock1337.dto.stockHistoryDTO;
import stock1337.stock1337.enums.HistoryType;
import stock1337.stock1337.model.stockHistory;
import stock1337.stock1337.repository.StockHistoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/history")
@RequiredArgsConstructor
public class StockHistoryController {

    private final StockHistoryRepository stockHistoryRepository;
    private final StockHistoryMapper stockHistoryMapper;

    @GetMapping("/recent")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<stockHistoryDTO>> getRecentHistory(
            @RequestParam(required = false, defaultValue = "false") boolean all) {

        List<stockHistory> historyList = all
                ? stockHistoryRepository.findAllByOrderByRecordedAtDesc()
                : stockHistoryRepository.findRecentHistory(LocalDateTime.now().minusDays(30));

        List<stockHistoryDTO> dtos = historyList.stream()
                .map(stockHistoryMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }






}