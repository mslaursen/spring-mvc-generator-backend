package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.User;
import com.code.springmvcgenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) throws Exception {
        if (userRepository.emailTaken(user.getEmail()).isEmpty()) {
            return userRepository.save(user);
        } else {
            throw new Exception("Email taken");
        }
    }

    public User verifyLogin(User user) throws LoginException {
        return userRepository.findByEmailPasswordMatch(user.getEmail(), user.getPassword())
                .orElseThrow(() -> new LoginException("User not found"));
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
