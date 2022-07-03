package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.User;
import com.code.springmvcgenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User update(User toUpdate) {
        return userRepository.save(toUpdate);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
