package stock1337.stock1337.Service;

import stock1337.stock1337.DTO.StockRequest;
import stock1337.stock1337.Model.Stock;

import java.util.List;

public interface StockService {

    List<Stock> getStockByDepartement(Long departementId);
    List<Stock> getAllStocks();
    Stock updateStock(Long id, StockRequest request);
    Stock createStock(StockRequest request);
}
