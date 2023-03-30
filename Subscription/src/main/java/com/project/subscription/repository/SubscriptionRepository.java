package com.project.subscription.repository;

import com.project.subscription.models.SSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<SSubscription, Long> {
    Optional<SSubscription> findByUserId(long userId);
}
