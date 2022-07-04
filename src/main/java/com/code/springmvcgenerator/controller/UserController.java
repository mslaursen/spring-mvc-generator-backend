package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.User;
import com.code.springmvcgenerator.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.ok()
            .body(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> fetchAll() {
        List<User> found = userService.findAll();
        return ResponseEntity.ok()
            .body(found);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> fetchById(@PathVariable Long id) {
        User found = userService.findById(id);
        return ResponseEntity.ok()
            .body(found);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateById(@RequestBody User user, @PathVariable Long id) {
        User toUpdate = userService.findById(id);
        toUpdate.setEmail(user.getEmail());
        toUpdate.setPassword(user.getPassword());

        User updated = userService.update(toUpdate);
        return ResponseEntity.ok()
            .body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok()
            .build();
    }
}
