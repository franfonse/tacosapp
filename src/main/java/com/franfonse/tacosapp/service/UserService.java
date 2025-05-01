package com.franfonse.tacosapp.service;

import com.franfonse.tacosapp.model.User;
import com.franfonse.tacosapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean validateUsernameFormat(String username) {
        return username.length() <= 60 && !username.contains(" ") && !Character.isDigit(username.charAt(0));
    }

    public String sanitizeUsername(String username){
        return username.toLowerCase();
    }

    public User createUser(String username, String encodedPassword) {
        return userRepository.save(new User(username, encodedPassword));
    }
}