package com.franfonse.tacosapp.service;

import com.franfonse.tacosapp.model.User;
import com.franfonse.tacosapp.respository.UserRepository;
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

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findOrCreateUsername(String username) {
        return userRepository.findByUsername(username).orElseGet(() -> userRepository.save(new User(username)));
    }

    public String sanitizeUsername(String username) {
        return username.toLowerCase();
    }


}
