package com.example.CRUD.Operation.controller;

import com.example.CRUD.Operation.model.AuthUser;
import com.example.CRUD.Operation.repository.AuthUserRepository;
import com.example.CRUD.Operation.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthUserRepository repo;

    @Autowired
    private JwtUtil jwt;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthUser user) {

        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password required");
        }

        if (repo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists");
        }

        repo.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthUser user) {

        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password required");
        }

        return repo.findByUsername(user.getUsername())
                .map(dbUser -> {
                    if (!dbUser.getPassword().equals(user.getPassword())) {
                        return ResponseEntity.status(401).body("Invalid credentials");
                    }

                    String token = jwt.generateToken(dbUser.getUsername(), dbUser.getRole());
                    return ResponseEntity.ok(token);
                })
                .orElseGet(() -> ResponseEntity.status(404).body("User not found"));
    }
}
