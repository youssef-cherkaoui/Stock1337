package stock1337.stock1337.Service.Impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock1337.stock1337.Model.Stock;
import stock1337.stock1337.Repository.StockRepository;
import stock1337.stock1337.Service.StockService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public List<Stock> getStockByDepartement(Long departementId) {
        return stockRepository.findByDepartementId(departementId);
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Stock updateStock(Long id, Stock stockDetails) {

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        stock.setQuantity(stockDetails.getQuantity());
        stock.setLocalisation(stockDetails.getLocalisation());
        return stockRepository.save(stock);
    }
}
