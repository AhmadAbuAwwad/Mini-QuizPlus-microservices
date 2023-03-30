package com.example.loginService.repository;

import com.example.loginService.models.ERole;
import com.example.loginService.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * @param name
     * @return
     */
    Optional<Role> findByRoleName(ERole name);
}
