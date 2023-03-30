package com.project.subscription.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.subscription.config.Message;
import com.project.subscription.config.MessageCode;
import com.project.subscription.controller.request.SubscriptionRequest;
import com.project.subscription.models.SSubscription;
import com.project.subscription.service.SubscriptionFacadeService;
import com.project.subscription.util.Mapper;
import com.project.subscription.util.WebhookResponse;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionFacadeService service;

    @Autowired
    private AmazonSNSClient snsClient;

    @Autowired
    Mapper mapper;

    @Value("${SNS-Arn}")
    String TOPIC_ARN;

    /**
     * @param subscriptionRequest
     * @return
     * @throws StripeException
     * @throws JsonProcessingException
     */
    @PostMapping("/subscribeAndPay")
    public String subscribeAndPay(@RequestBody
                                  SubscriptionRequest subscriptionRequest) throws StripeException, JsonProcessingException {
        SSubscription subscription = service.subscribe(subscriptionRequest.getUserId(),
                subscriptionRequest.getPlanId(), subscriptionRequest.getCardData());

        Message message = new Message(subscription.getUserId(), MessageCode.SUBSCRIBED, subscription.getPlan().getPlanType());
        String jsonMessage = (new ObjectMapper()).writeValueAsString(message);

        PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, jsonMessage);
        snsClient.publish(publishRequest);

        return "You Subscription was activated and Its ID = " + subscription.getId();
    }

    /**
     * @param subscriptionRequest
     * @return
     * @throws StripeException
     * @throws JsonProcessingException
     */
    @PostMapping("/changePlan")
    public String changePlan(@RequestBody
                             SubscriptionRequest subscriptionRequest) throws StripeException, JsonProcessingException {
        SSubscription subscription = service.changePlan(subscriptionRequest.getUserId(),
                subscriptionRequest.getPlanId(), subscriptionRequest.getCardData());

        Message message = new Message(subscription.getUserId(), MessageCode.CHANGED_PLAN, subscription.getPlan().getPlanType());
        String jsonMessage = (new ObjectMapper()).writeValueAsString(message);

        PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, jsonMessage);
        snsClient.publish(publishRequest);


        return "You Subscription was activated and Its ID = " + subscription.getId();
    }


    /**
     * @return
     */
    @PostMapping("/locateCharge")
    public SSubscription locateCharge(@RequestBody WebhookResponse response) {
        return service.validSubscription(response);
    }

    /**
     * @param userId
     * @return
     * @throws StripeException
     * @throws JsonProcessingException
     */
    @PostMapping("/cancel-subscription/{userId}")
    public boolean cancelSubscription(@PathVariable long userId) throws StripeException, JsonProcessingException {
        boolean cancelStatus = service.cancel(userId);
        Message message = new Message(userId, MessageCode.CANCELED);
        String jsonMessage = (new ObjectMapper()).writeValueAsString(message);

        PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, jsonMessage);
        snsClient.publish(publishRequest);

        return cancelStatus;
    }

}
