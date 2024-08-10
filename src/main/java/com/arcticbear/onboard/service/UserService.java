package com.arcticbear.onboard.service;

import com.arcticbear.onboard.exception.UserNotFoundException;
import com.arcticbear.onboard.entity.User;
import com.arcticbear.onboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Customer not found with id : " + id));
    }

    public boolean existById(String id) {
        return userRepository.existsById(id);
    }

    public void update(String id, User user) {
        if(!existById(id)){
            throw new UserNotFoundException("Customer not found with id : " + id);
        }
        user.setId(id);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(String id) {
        if(!existById(id)){
            throw new UserNotFoundException("Customer not found with id : " + id);
        }
        userRepository.deleteById(id);
    }
}
