package com.arcticbear.onboard.service;

import com.arcticbear.onboard.entity.User;
import com.arcticbear.onboard.exception.UserNotFoundException;
import com.arcticbear.onboard.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //when login happens this method resolves the input parameter and map it to the field we intend for username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with email : " + username));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRoles());
    }
}
