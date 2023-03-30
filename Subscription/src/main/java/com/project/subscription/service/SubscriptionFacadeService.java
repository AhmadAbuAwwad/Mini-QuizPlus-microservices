package com.project.subscription.service;

import com.project.subscription.controller.request.CardData;
import com.project.subscription.models.*;
import com.project.subscription.util.Mapper;
import com.project.subscription.util.WebhookResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionFacadeService {

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    PlanService planService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    SCustomerService sCustomerService;

    @Autowired
    Mapper mapper;


    @Value("${stripe-key}")
    private String stripeKey;

    /**
     * @param userId
     * @return
     * @throws StripeException
     */
    public SSubscription createEmptySubscription(long userId) throws StripeException {
        return subscriptionService.createSubscription(userId);
    }


    /**
     * @param response
     * @return
     */
    public SSubscription validSubscription(WebhookResponse response) {
        SCustomer customer = sCustomerService.getCustomerByStripeId((String) response.getData().getObject().get("customer"));
        SSubscription subscription = subscriptionService.getSubcriptionByUserId(customer.getUserId());

        if (response.getType().equals("charge.succeeded")) {
            subscription.setStatus(EStatus.Active);
        } else {
            subscription.setStatus(EStatus.PastDue);
        }
        subscriptionService.updateSubscriptionStatus(subscription);
        return subscription;
    }

    /**
     * @param userId
     * @param planId
     * @return
     * @throws StripeException
     */
    @Transactional
    public SSubscription subscribe(long userId, long planId, CardData cardData) throws StripeException {
        Stripe.apiKey = stripeKey;

        //  1- Create/Get Stripe Customer
        SCustomer sCustomer = sCustomerService.getCustomerByUserId(userId, cardData);

        //  2- Get Plan of Sub
        Plan plan = planService.getPlanById(planId);

        //  3- Charge User
        Payment payment = paymentService.chargeByACustomer(sCustomer, plan.getPrice());

        SSubscription subscription = null;
        try {
            //  4- Activate Subscription
            if (payment.getChargeId() != null) {
                subscription = subscriptionService.createSubscription(payment, plan);
            }


        } catch (Exception e) {
            //  Make Refund HERE
            paymentService.refund(payment.getChargeId());
            e.printStackTrace();
            throw new RuntimeException();
        }
        return subscription;
    }

    /**
     * @param userId
     * @return
     * @throws StripeException
     */
    @Transactional
    public boolean cancel(long userId) throws StripeException {
        Stripe.apiKey = stripeKey;

        //  1- Create/Get Stripe Customer
        SCustomer sCustomer = sCustomerService.getCustomerByUserId(userId, null);

        //  2- Get Subscription
        SSubscription subscription = subscriptionService.getSubcriptionByUserId(userId);

        //  3- Cancel it
        return subscriptionService.cancelSubscription(subscription.getId(), subscription.getStripeSubscriptionId());
    }


    /**
     * @param userId
     * @param planId
     * @return
     * @throws StripeException
     */
    @Transactional
    public SSubscription changePlan(long userId, long planId, CardData cardData) throws StripeException {
        Stripe.apiKey = stripeKey;

        //  1- Create/Get Stripe Customer
        SCustomer sCustomer = sCustomerService.getCustomerByUserId(userId, cardData);

        //  2- Get Plan of Sub
        Plan plan = planService.getPlanById(planId);

        //  3- Get Subscription
        SSubscription subscription = subscriptionService.getSubcriptionByUserId(sCustomer.getUserId());

        //  4- Change Plan
        subscription.setPlan(plan);

        return subscription;
    }
}
