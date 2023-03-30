package com.example.loginService.service;

import com.example.loginService.models.ERole;
import com.example.loginService.models.Role;
import com.example.loginService.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    /**
     * @param roleName
     * @return
     */
    public Role findRole(ERole roleName) {
        Role role = roleRepository.findByRoleName(ERole.ROLE_CLIENT).get();
        return role;
    }
}
