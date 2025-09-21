package com.Alsainey.ShimmerShine.repositories.subscription;

import com.Alsainey.ShimmerShine.entities.subscription.Subscription;
import com.Alsainey.ShimmerShine.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // Find active subscription for a user
    @Query("SELECT s FROM Subscription s " +
            "WHERE s.user.id = :userId AND s.status = 'ACTIVE'")
    Optional<Subscription> findActiveByUserId(User user);

    // Find all active subscriptions
    @Query("SELECT s FROM Subscription s WHERE s.status = 'ACTIVE'")
    List<Subscription> findAllActive();

    // Find all subscriptions for a user (history)
    List<Subscription> findByUserId(Long userId);
}
