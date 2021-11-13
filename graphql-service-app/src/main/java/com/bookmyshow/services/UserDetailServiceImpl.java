package com.bookmyshow.services;

import com.bookmyshow.exceptions.UserNotFoundException;
import com.bookmyshow.models.User;
import com.bookmyshow.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final Logger log = LogManager.getLogger(UserDetailServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        log.info("Load User By name method is called");
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    user.get().getEmail(), user.get().getPassword(), true, true,
                    true, true, getGrantedAuthorities(user.get()));
        }
        log.info("User not found");
        throw new UserNotFoundException("Incorrect Email/Password");
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        log.info("Method Grant Authorities is called \n Email: " + user.getEmail() + "\n Role: " + user.getRole().toString());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }
}