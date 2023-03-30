package com.project.subscription.service;

import com.project.subscription.models.Plan;
import com.project.subscription.repository.PlanRepository;
import com.project.subscription.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    Mapper mapper;

    /**
     * @param planId
     * @return
     */
    public Plan getPlanById(long planId) {
        return planRepository.findById(planId).get();
    }

    /**
     * @param planId
     * @return
     */
    public List<Plan> getAllPlans(long planId) {
        return planRepository.findAll();
    }
}
