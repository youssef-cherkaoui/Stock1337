package stock1337.stock1337.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stock1337.stock1337.DTO.StockRequest;
import stock1337.stock1337.Model.Stock;
import stock1337.stock1337.Service.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Stock>> getAll() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }


    @GetMapping("departement/{id}")
    public ResponseEntity<List<Stock>> getByDept(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(stockService.getStockByDepartement(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Stock> updateStock(
            @PathVariable Long id,
            @RequestBody StockRequest request
    ){
        return ResponseEntity.ok(stockService.updateStock(id, request));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Stock> createStock(
            @RequestBody StockRequest request
            ){
        return ResponseEntity.ok(stockService.createStock(request));
    }



}
