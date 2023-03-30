package com.project.subscription.security.services;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class AuthUser implements org.springframework.security.core.userdetails.UserDetails {

    private String email;


    /**
     * @param email
     */
    public AuthUser(String email) {
        this.email = email;
    }


    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }


    /**
     * @return
     */
    @Override
    public String getPassword() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getUsername() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }


    /**
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
