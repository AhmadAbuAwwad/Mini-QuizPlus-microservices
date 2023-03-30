package com.project.subscription.controller.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SubscriptionRequest {
    Long subscriptionId;
    Long userId;
    Long planId;
    CardData cardData;
 }
