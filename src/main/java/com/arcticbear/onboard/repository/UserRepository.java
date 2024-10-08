package com.arcticbear.onboard.repository;

import com.arcticbear.onboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String username);
    List<User> findAllByOrderByIdDesc();
}
