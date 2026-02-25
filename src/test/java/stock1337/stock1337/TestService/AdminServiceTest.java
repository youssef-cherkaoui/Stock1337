package stock1337.stock1337.TestService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import stock1337.stock1337.Enums.Role;
import stock1337.stock1337.Model.Admin;
import stock1337.stock1337.Repository.AdminRepository;
import stock1337.stock1337.Service.Impl.AdminServiceImpl;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        admin.setId(1L);
        admin.setName("Youssef");
        admin.setEmail("Youssef@1337.ma");
        admin.setPassword("password");
    }

    @Test
    void ShouldCreateAdminSuccessfully() {
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);
        Admin savedAdmin = adminService.createAdmin(admin);

        assertNotNull(savedAdmin);
        assertEquals(Role.ADMIN, savedAdmin.getRole());
        verify(adminRepository, times(1)).save(admin);

    }

    @Test
    void ShouldGetAdminByIdSuccessfully() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        Admin foundAdmin = adminService.getAdminById(1L);

        assertEquals("Youssef", foundAdmin.getName());
        verify(adminRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenAdminNotFound(){
        when (adminRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            adminService.getAdminById(99L);
        });
        assertEquals("No Admin found with id: 99", exception.getMessage());
    }

    @Test
    void ShouldUpdateAdminSuccessfully() {
        Admin update = new Admin();
        update.setName("Youssef Update");
        update.setEmail("YoussefUpdate@1337.ma");

        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin result = adminService.updateAdmin(1L, update);

        assertEquals("Youssef Update", result.getName());
        verify(adminRepository).save(admin);
    }

    @Test
    void ShouldDeleteAdminSuccessfully() {
        adminService.deleteAdmin(1L);
        verify(adminRepository , times(1)).deleteById(1L);
    }
}
