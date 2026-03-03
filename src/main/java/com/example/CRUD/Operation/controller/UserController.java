package com.example.CRUD.Operation.controller;

import com.example.CRUD.Operation.model.User;
import com.example.CRUD.Operation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/page")
    public Page<User> getUsers(
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @GetMapping("/search/name")
    public Page<User> searchByName(
            @RequestParam String name,
            Pageable pageable) {
        return userService.searchByName(name, pageable);
    }

    @GetMapping("/search/email")
    public Page<User> searchByEmail(
            @RequestParam String email,
            Pageable pageable) {
        return userService.searchByEmail(email, pageable);
    }

    @PostMapping(value = "/bulk", consumes = "application/json")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users) {
        return ResponseEntity.ok(userService.saveAll(users));
    }

    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // CREATE user
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // UPDATE user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User existing = userService.getUserById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
