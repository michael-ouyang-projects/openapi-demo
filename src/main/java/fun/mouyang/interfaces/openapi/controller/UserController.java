package fun.mouyang.interfaces.openapi.controller;

import fun.mouyang.interfaces.rest.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {
    @Override
    public ResponseEntity<User> getUserById(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("Michael");
        return ResponseEntity.ok(user);
    }
}
