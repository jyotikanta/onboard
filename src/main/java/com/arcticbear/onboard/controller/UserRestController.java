package com.arcticbear.onboard.controller;

import com.arcticbear.onboard.entity.User;
import com.arcticbear.onboard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<String> index(){
        return new ResponseEntity<>("Hello From Arctic!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user){
        userService.save(user);
        return new ResponseEntity<>("Customer registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getCustomer(@PathVariable String id){
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable String id, @Valid @RequestBody User user){
        userService.update(id, user);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id){
        userService.delete(id);
        return new ResponseEntity<>("Customer deleted successfully" , HttpStatus.OK);
    }

    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<User>> getCustomers(){
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
