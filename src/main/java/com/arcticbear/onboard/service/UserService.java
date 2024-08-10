package com.arcticbear.onboard.service;

import com.arcticbear.onboard.dto.RegistrationRequest;
import com.arcticbear.onboard.entity.Role;
import com.arcticbear.onboard.entity.User;
import com.arcticbear.onboard.exception.UserNotFoundException;
import com.arcticbear.onboard.repository.RoleRepository;
import com.arcticbear.onboard.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegistrationRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        System.out.println(request.getPassword());//password123
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMobile(request.getMobile());

        Role userRole = roleRepository.findByName("USER").orElse(new Role("USER")); //TODO get from ROLE enum
        user.setRoles(new HashSet<>());
        user.getRoles().add(userRole);

        userRepository.save(user);
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id : " + id));
    }

    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

    public void update(String id, RegistrationRequest request) {
        if(!existsById(id)){
            throw new UserNotFoundException("User not found with id : " + id);
        }
        User existingUser = userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException("User not found with id : " + id)
        );

        existingUser.setEmail(request.getEmail());
        existingUser.setMobile(request.getMobile());
        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        if(request.getRoles()!=null && !request.getRoles().isEmpty()){
            Set<Role> roles = new HashSet<>();
            for(String roleName: request.getRoles()){
                Role role = roleRepository.findByName(roleName).
                        orElseThrow(()-> new RuntimeException("Role not found : "+roleName));
                roles.add(role);
            }
            existingUser.setRoles(roles);
        }

        userRepository.save(existingUser);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(String id) {
        if(!existsById(id)){
            throw new UserNotFoundException("User not found with id : " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with email : " + username));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRoles());
    }
}
