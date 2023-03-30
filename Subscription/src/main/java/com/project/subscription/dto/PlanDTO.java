package com.project.subscription.dto;

import com.project.subscription.models.EPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PlanDTO {
    private Integer id;
    private EPlan planType;
    private Double price;
    public Integer days;
    private String stripeId;
}
