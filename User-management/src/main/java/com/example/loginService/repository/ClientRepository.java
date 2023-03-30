package com.example.loginService.repository;

import com.example.loginService.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * @param username
     * @return
     */
    Optional<Client> findByEmail(String username);
}
