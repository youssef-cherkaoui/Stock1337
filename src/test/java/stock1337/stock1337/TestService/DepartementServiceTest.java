package stock1337.stock1337.TestService;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import stock1337.stock1337.Model.Departement;
import stock1337.stock1337.Repository.DepartementRepository;
import stock1337.stock1337.Service.Impl.DepartementServiceImpl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DepartementServiceTest {


    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;
    private Departement departement;

    @BeforeEach
    void setUp() {
        departement = new Departement();
        departement.setId(1L);
        departement.setName("IT-Departement");
    }


    @Test
    void ShouldGetAllDepartements() {
        when (departementRepository.findAll()).thenReturn(Arrays.asList(departement));

        List<Departement> result = departementService.getAllDepartement();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("IT-Departement", result.get(0).getName());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void ShouldSaveDepartementSuccessfully() {
        when (departementRepository.save(any(Departement.class))).thenReturn(departement);

        Departement saved = departementService.saveDepartement(departement);

        assertNotNull(saved);
        assertEquals("IT-Departement", saved.getName());
        verify(departementRepository).save(departement);
    }

    @Test
    void ShouldUpdateDepartementSuccessfully() {

        Departement depUpdate = new Departement();
        depUpdate.setName("Departement-IT-");

        when(departementRepository.findById(1L)).thenReturn(Optional.of(departement));
        when(departementRepository.save(any(Departement.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Departement result = departementService.updateDepartement(1L, depUpdate);

        assertNotNull(result);
        assertEquals("Departement-IT-", result.getName());
        verify(departementRepository).findById(1L);
        verify(departementRepository).save(any(Departement.class));


    }

    @Test
    void ShouldThrowExceptionWhenUpdatingNonExistingDepartement() {

        when(departementRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            departementService.updateDepartement(99L, new Departement());
        });
        verify(departementRepository, never()).save(any());
    }


    @Test
    void ShouldDeleteDepartementSuccessfully() {
        departementService.deleteDepartement(1L);
        verify(departementRepository, times(1)).deleteById(1L);
    }


}
