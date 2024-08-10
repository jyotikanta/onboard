package com.arcticbear.onboard.repository;

import com.arcticbear.onboard.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Short> {
    Role findByName(String name);
}
