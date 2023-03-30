package com.project.subscription.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscription", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
})
public class SSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EStatus status;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private String stripeSubscriptionId;

    //    Plan
    @ManyToOne(fetch = FetchType.LAZY)
    Plan plan;
}
