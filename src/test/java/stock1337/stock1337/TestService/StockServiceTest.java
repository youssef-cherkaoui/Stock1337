package stock1337.stock1337.TestService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import stock1337.stock1337.DTO.StockRequest;
import stock1337.stock1337.Model.Departement;
import stock1337.stock1337.Model.Stock;
import stock1337.stock1337.Repository.DepartementRepository;
import stock1337.stock1337.Repository.StockRepository;
import stock1337.stock1337.Service.Impl.StockServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    private Departement departement;
    private Stock stock;
    private StockRequest stockRequest;

    @BeforeEach
    void setUp() {
        departement = new Departement();
        departement.setId(1L);
        departement.setName("Informatique");

        stock = new Stock();
        stock.setId(10L);
        stock.setName("Stock Principal");
        stock.setLocalisation("Batiment A");
        stock.setDepartement(departement);

        stockRequest = new StockRequest();
        stockRequest.setName("Stock Nouveau");
        stockRequest.setLocalisation("Batiment B");
        stockRequest.setDepartementId(1L);
    }


    @Test
    void ShouldCreateStockSuccessfully() {

        when(departementRepository.findById(1L)).thenReturn(Optional.of(departement));
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Stock created = stockService.createStock(stockRequest);

        assertNotNull(created);
        assertEquals("Stock Nouveau", created.getName());
        assertEquals(departement, created.getDepartement());
        assertNotNull(created.getArticles());
        verify(stockRepository).save(any(Stock.class));
    }


    @Test
    void ShouldUpdateStockFieldsIndividually(){
        StockRequest update = new StockRequest();
        update.setName("Stock Updated");

        when(stockRepository.findById(10L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Stock updated = stockService.updateStock(10L, update);

        assertEquals("Stock Updated", updated.getName());
        assertEquals("Batiment A", updated.getLocalisation());
        verify(stockRepository).save(stock);
    }

    @Test
    void shouldThrowExceptionWhenDepartementNotFoundDuringCreation() {

        when(departementRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class, () -> stockService.createStock(stockRequest));
        verify(stockRepository, never()).save(any());
    }

    @Test
    void shouldGetStockByDepartement() {

        List<Stock> stocks = List.of(stock);
        when(stockRepository.findByDepartementId(1L)).thenReturn(stocks);


        List<Stock> result = stockService.getStockByDepartement(1L);


        assertEquals(1, result.size());
        assertEquals("Stock Principal", result.get(0).getName());
    }


}
