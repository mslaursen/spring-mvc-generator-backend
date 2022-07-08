package com.code.springmvcgenerator.repository;


import com.code.springmvcgenerator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.email = ?1 " +
            "AND u.password = ?2")
    Optional<User> findByEmailPasswordMatch(String email, String password);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> emailTaken(String email);
}
