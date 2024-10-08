package com.gokulinfocare.userapp.service.impl;

import com.gokulinfocare.userapp.exception.InvalidUserDetailsException;
import com.gokulinfocare.userapp.model.User;
import com.gokulinfocare.userapp.repository.UserRepository;
import com.gokulinfocare.userapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            log.warn("Attempt to save user failed: Phone number already exists {}", user.getPhoneNumber());
            throw new InvalidUserDetailsException("Phone number already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("Attempt to save user failed: Email already exists {}", user.getEmail());
            throw new InvalidUserDetailsException("Email already exists");
        }
        try {
            User savedUser = userRepository.save(user);
            log.info("User saved successfully: {}", savedUser);
            return savedUser;
        } catch (Exception e) {
            log.error("User not saved due to an error: {}", e.getMessage());
            throw new RuntimeException("Error saving user");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        Collections.reverse(users);
        return users;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
