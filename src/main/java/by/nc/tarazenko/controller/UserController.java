package by.nc.tarazenko.controller;

import by.nc.tarazenko.entity.User;
import by.nc.tarazenko.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        log.debug("Get all users.");
        List<User> users = userService.getAll();
        log.debug("Users - {}", users);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        log.debug("Create user in: user - {}", user);
        User userResult = userService.create(user);
        log.debug("Create user out: result user - {}", userResult);
        return ResponseEntity.ok(userResult);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable int id) {
        user.setId(id);
        log.debug("Update user in: user - {}", user);
        User userResult = userService.update(user);
        log.debug("Update user out: result user - {}", userResult);
        return ResponseEntity.ok(userResult);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(@PathVariable int id) {
        log.debug("Get user in: id - {}", id);
        User user = userService.getById(id);
        log.debug("Get user out: result - {}", user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        log.debug("Delete user in: id - {}", id);
        userService.deleteById(id);
        log.debug("Delete user id - {}", id);
        return ResponseEntity.ok().build();
    }
}
