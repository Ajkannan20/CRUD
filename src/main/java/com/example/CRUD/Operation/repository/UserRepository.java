package com.example.CRUD.Operation.repository;

import com.example.CRUD.Operation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByName(String name);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
