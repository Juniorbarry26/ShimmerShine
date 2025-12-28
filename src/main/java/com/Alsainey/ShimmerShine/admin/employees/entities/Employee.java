package com.Alsainey.ShimmerShine.admin.employees.entities;

import com.Alsainey.ShimmerShine.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "employees")
@EntityListeners(AuditingEntityListener.class)
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String position;
    private String location;
    private String phoneNumber;
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal salary;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDate updatedAt;

}
