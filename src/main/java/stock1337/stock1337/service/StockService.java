package stock1337.stock1337.service;

import stock1337.stock1337.dto.StockRequest;
import stock1337.stock1337.model.Stock;

import java.util.List;

public interface StockService {

    List<Stock> getStockByDepartement(Long departementId);
    List<Stock> getAllStocks();
    Stock updateStock(Long id, StockRequest request);
    Stock createStock(StockRequest request);
}
