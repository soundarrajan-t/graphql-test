package com.bookmyshow.services;

import com.bookmyshow.models.User;
import com.bookmyshow.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    private final Logger log = LogManager.getLogger(UserService.class);

    public String createUser(User user) {
        log.info("Method create user is called");

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        User newUser = userRepository.save(user);
        return newUser.getName();
    }

    public Optional<User> getUser(String email) {
        log.info("Method get user by Email is called");
        return userRepository.findByEmail(email);
    }
}