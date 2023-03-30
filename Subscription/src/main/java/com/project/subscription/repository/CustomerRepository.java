package com.project.subscription.repository;

import com.project.subscription.models.SCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<SCustomer, Long> {
    Optional<SCustomer> findByUserId(long userId);
    Optional<SCustomer> findByStripeId(String stripId);
}
