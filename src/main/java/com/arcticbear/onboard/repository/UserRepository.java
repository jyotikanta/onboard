package com.arcticbear.onboard.repository;

import com.arcticbear.onboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
