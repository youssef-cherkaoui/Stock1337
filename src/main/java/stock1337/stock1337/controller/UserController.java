package stock1337.stock1337.controller;

import stock1337.stock1337.model.User;
import stock1337.stock1337.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/User")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
}
