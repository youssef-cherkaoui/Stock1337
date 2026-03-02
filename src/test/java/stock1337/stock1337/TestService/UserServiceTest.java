package stock1337.stock1337.TestService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import stock1337.stock1337.enums.Role;
import stock1337.stock1337.model.User;
import stock1337.stock1337.repository.UserRepository;
import stock1337.stock1337.service.Impl.UserServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
 class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Youssef");
        user.setEmail("Yous@1337.ma");
        user.setPassword("RawPassword");
        user.setRole(Role.USER);
    }

    @Test
    void ShouldCreateUserWithDefaultRole() {
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User created = userService.createUser(user);

        assertNotNull(created);
        assertEquals(Role.USER, created.getRole());
        verify(userRepository).save(user);
    }

    @Test
    void ShouldGetUserByEmailSuccessfuly(){
        when(userRepository.findByEmail("Yous@1337.ma")).thenReturn(Optional.of(user));

        User found = userService.getUserByEmail("Yous@1337.ma");

        assertNotNull(found);
        assertEquals("Yous@1337.ma", found.getEmail());

    }

    @Test
    void ShouldUpdateUserAndEncodeNewPassword() {
        User update = new User();
        update.setName("Youssef Essaddik");
        update.setEmail("Youssef@1337.ma");
        update.setPassword("NewSecret1337");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("NewSecret1337")).thenReturn("NewSecret_Encoded1337");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updateUser = userService.updateUser(1L, update);

        assertEquals("Youssef Essaddik", updateUser.getName());
        assertEquals("Youssef@1337.ma", updateUser.getEmail());
        assertEquals("NewSecret_Encoded1337", updateUser.getPassword());

        verify(passwordEncoder).encode("NewSecret1337");
        verify(userRepository).save(any(User.class));
    }


    @Test
    void ShouldThrowExceptionWhenUserNotFoundById() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(99L);
        });

        assertTrue(exception.getMessage().contains("User not found with id: 99"));
    }


    @Test
    void ShouldDeleteUser(){
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

}
