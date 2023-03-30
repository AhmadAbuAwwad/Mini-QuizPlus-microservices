package com.project.subscription.service;

import com.project.subscription.dto.PlanDTO;
import com.project.subscription.models.EStatus;
import com.project.subscription.models.Payment;
import com.project.subscription.models.Plan;
import com.project.subscription.models.SSubscription;
import com.project.subscription.repository.SubscriptionRepository;
import com.project.subscription.util.Mapper;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    Mapper mapper;

    /**
     * CreateSubscription
     *
     * @param userId
     * @return
     */
    public SSubscription createSubscription(long userId) {
        Optional<SSubscription> subscription = subscriptionRepository.findByUserId(userId);
        if (subscription.isPresent())
            return subscription.get();

        SSubscription subscription1 = new SSubscription();
        subscription1.setUserId(userId);
        subscription1.setStatus(EStatus.InActive);
        return subscriptionRepository.save(subscription1);
    }

    /**
     * ActiveSubscription
     *
     * @param payment
     * @param planDTO
     * @return
     */
    public SSubscription createSubscription(Payment payment, Plan planDTO) throws StripeException {

        Subscription subscriptionStripe = null;
        try {
            List<Object> items = new ArrayList<>();

            Map<String, Object> item = new HashMap<>();
            item.put("price", planDTO.getStripeId());
            items.add(item);

            Map<String, Object> params = new HashMap<>();

            params.put("customer", payment.getCustomer().getStripeId());
            params.put("items", items);

            subscriptionStripe = Subscription.create(params);
        } catch (
                Exception e) {
            e.printStackTrace();
//            throw new RuntimeException();
        }

        SSubscription subscription = new SSubscription();

        //  Get Dates
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        Date nextMonthDate = calendar.getTime();

//          TODO Get the Stripe Sub String ID
        subscription.setStripeSubscriptionId(subscriptionStripe.getId());
        subscription.setStartDate(currentDate);
        subscription.setEndDate(nextMonthDate);
//        subscription.setStatus(EStatus.Active);
        subscription.setPlan(mapper.map(Plan.class, planDTO));

        return subscriptionRepository.save(subscription);
    }

    /**
     * cancelSubscription
     *
     * @param subscriptionId
     * @return
     */
    public boolean cancelSubscription(long subscriptionId, String stripeSubscriptionId) {
        boolean subscriptionStatus;

        try {
            Subscription subscription = Subscription.retrieve(stripeSubscriptionId);
            subscription.cancel();
            subscriptionRepository.deleteById(subscriptionId);
            subscriptionStatus = true;
        } catch (Exception e) {
            e.printStackTrace();
            subscriptionStatus = false;
        }
        return subscriptionStatus;
    }

    /**
     * changeSubscription
     *
     * @param subscriptionId
     * @return
     */
    public SSubscription changeSubscription(long subscriptionId, PlanDTO planDTO) {
        SSubscription subscription = subscriptionRepository.findById(subscriptionId).get();
        subscription.setPlan(mapper.map(Plan.class, planDTO));
        return subscriptionRepository.save(subscription);
    }

    public SSubscription getSubcriptionByUserId(long userId) {
        return subscriptionRepository.findByUserId(userId).get();
    }

    public SSubscription updateSubscriptionStatus(SSubscription subscription) {
        return subscriptionRepository.save(subscription);
    }
}
