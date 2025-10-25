package com.Alsainey.ShimmerShine.entities.employee;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "employees")
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
    private Integer phoneNumber;
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal salary;

    @CreatedDate
    @Column(nullable = false, insertable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDate updatedAt;

}
