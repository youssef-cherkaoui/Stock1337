package stock1337.stock1337.Service.Impl;


import stock1337.stock1337.Enums.Role;
import stock1337.stock1337.Model.User;
import stock1337.stock1337.Repository.UserRepository;
import stock1337.stock1337.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;



    @Override
    public User createUser(User user) {
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long Id, User user) {
        User existUser = getUserById(Id);
        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        existUser.setPassword(user.getPassword());

        return userRepository.save(existUser);
    }

    @Override
    public void deleteUser(Long Id) {
        userRepository.deleteById(Id);

    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
