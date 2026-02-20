package stock1337.stock1337.Service;

import stock1337.stock1337.Model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long Id ,User user);
    void deleteUser(Long Id);
    User getUserByEmail(String email);
}
