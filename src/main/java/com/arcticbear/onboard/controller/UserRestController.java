package com.arcticbear.onboard.controller;

import com.arcticbear.onboard.dto.RegistrationRequest;
import com.arcticbear.onboard.dto.UserDTO;
import com.arcticbear.onboard.entity.User;
import com.arcticbear.onboard.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ResponseEntity<String> updateUser(@PathVariable String id, @Valid @RequestBody RegistrationRequest request, HttpServletRequest httpRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Invalidate the current session
            httpRequest.getSession().invalidate();
            System.out.println("Session invalidated for user: " + authentication.getName() + " : " + request.getEmail());
        }
        userService.update(id, request);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        userService.delete(id);
        return new ResponseEntity<>("Customer deleted successfully" , HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<User> users = userService.findAll();
        List<UserDTO> userDTOS = users.stream().map(user->{
            Set<String> roleNames = user.getRoles().stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toSet());
            return new UserDTO(user.getId(), user.getEmail(),user.getMobile(),roleNames);
        }).collect(Collectors.toList());

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllUsersSorted")
    public ResponseEntity<List<UserDTO>> getAllUsersSortedByCreationTime(){
        List<User> users = userService.findAllSortedByCreationTime();
        List<UserDTO> userDTOS = users.stream().map(user->{
            Set<String> roleNames = user.getRoles().stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toSet());
            return new UserDTO(user.getId(), user.getEmail(),user.getMobile(),roleNames);
        }).collect(Collectors.toList());

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }


}
