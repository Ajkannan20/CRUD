package com.example.CRUD.Operation.service;

import com.example.CRUD.Operation.exception.DuplicateResourceException;
import com.example.CRUD.Operation.model.User;
import com.example.CRUD.Operation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // CREATE
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (userRepository.existsByName(user.getName())) {
            throw new DuplicateResourceException("Name already exists");
        }

        return userRepository.save(user);
    }

    // UPDATE
    public User updateUser(Long id, User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        user.setId(id);
        return userRepository.save(user);
    }

    // DELETE
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // READ ALL
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // BULK SAVE
    public List<User> saveAll(List<User> users) {
        return userRepository.saveAll(users);
    }

    // PAGINATION
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // SEARCH
    public Page<User> searchByName(String name, Pageable pageable) {
        return userRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<User> searchByEmail(String email, Pageable pageable) {
        return userRepository.findByEmailContainingIgnoreCase(email, pageable);
    }

    // GET BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}