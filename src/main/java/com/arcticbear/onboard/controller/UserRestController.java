package com.arcticbear.onboard.controller;

import com.arcticbear.onboard.dto.RegistrationRequest;
import com.arcticbear.onboard.entity.User;
import com.arcticbear.onboard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserRestController {
    UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<String> index(){
        return new ResponseEntity<>("Hello From Arctic Vault!", HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("Welcome!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request){
        userService.registerUser(request);
        return new ResponseEntity<>("Customer registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @Valid @RequestBody RegistrationRequest request){
        userService.update(id, request);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        userService.delete(id);
        return new ResponseEntity<>("Customer deleted successfully" , HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
