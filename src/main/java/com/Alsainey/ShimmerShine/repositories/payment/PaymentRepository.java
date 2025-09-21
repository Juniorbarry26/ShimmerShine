package com.Alsainey.ShimmerShine.repositories.payment;

import com.Alsainey.ShimmerShine.entities.payment.Payment;
import com.Alsainey.ShimmerShine.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUser(User user);
}
