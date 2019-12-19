package by.nc.tarazenko.controller;

import by.nc.tarazenko.entity.User;
import by.nc.tarazenko.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("users")
public class UserController {
    private Logger logger = Logger.getLogger(RoomController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        logger.debug("Create user = " + user);
        return ResponseEntity.ok(userService.create(user));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable int id) {
        user.setId(id);
        return ResponseEntity.ok(userService.update(user));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
