package com.Alsainey.ShimmerShine.entities;

import com.Alsainey.ShimmerShine.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars", uniqueConstraints = {
        @UniqueConstraint(columnNames = "licensePlate")
})
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Many cars can belong to one user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    private String color;

    private Integer year;
}
