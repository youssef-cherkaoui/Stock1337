package stock1337.stock1337.Service.Impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock1337.stock1337.DTO.StockRequest;
import stock1337.stock1337.Model.Departement;
import stock1337.stock1337.Model.Stock;
import stock1337.stock1337.Repository.DepartementRepository;
import stock1337.stock1337.Repository.StockRepository;
import stock1337.stock1337.Service.StockService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final DepartementRepository departementRepository;

    @Override
    public List<Stock> getStockByDepartement(Long departementId) {
        return stockRepository.findByDepartementId(departementId);
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Stock updateStock(Long id, StockRequest request) {

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));


        if(request.getName() != null) stock.setName(request.getName());
        if(request.getLocalisation() != null) stock.setLocalisation(request.getLocalisation());
        if(request.getDepartementId() != null) {
            Departement dep = departementRepository.findById(request.getDepartementId())
                    .orElseThrow(() -> new RuntimeException("Departement not found"));
            stock.setDepartement(dep);
        }

        return stockRepository.save(stock);
    }

    @Override
    public Stock createStock(StockRequest request) {

        Departement dep = departementRepository.findById(request.getDepartementId()
                ).orElseThrow(() -> new RuntimeException("Departement not found"));

        Stock stock = new Stock();
        stock.setName(request.getName());
        stock.setLocalisation(request.getLocalisation());
        stock.setDepartement(dep);
        stock.setArticles(new ArrayList<>());
        return stockRepository.save(stock);
    }
}
