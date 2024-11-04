package com.example.userregistration.service;

import com.example.userregistration.model.User;
import com.example.userregistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling user related logic.
 * Manages user authentication and registration.
 * */
@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Validates user login credentials.
     * Checks if username exists or not, and if password matches.
     * @param userName Username to validate
     * @param password Password to check
     * @return true when credentials are valid
     * */
    public boolean validateUser(String userName, String password) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * Registers a new user in the system.
     * Hashes the password before storing it using BCrypt.
     * @param user The User being registered.
     * @return Saved the new user.
     *
    * */
    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Checks if the username already exists.
    public boolean doesUserExist(String userName) {
        return userRepository.findByUserName(userName) != null;
    }

    // Checks if the email already exists.
    public boolean doesEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User getUserByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
