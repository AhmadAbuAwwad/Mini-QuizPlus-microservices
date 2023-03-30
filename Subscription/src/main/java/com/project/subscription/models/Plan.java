package com.project.subscription.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plan")
public class Plan{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EPlan planType;

    @Column
    private String stripeId;


    @Column
    private Double price;


    @Column
    public Integer days;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    Set<SSubscription> SSubscriptions;
}