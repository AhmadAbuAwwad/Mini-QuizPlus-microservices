package com.project.subscription.service;

import com.project.subscription.controller.request.CardData;
import com.project.subscription.models.Payment;
import com.project.subscription.models.SCustomer;
import com.project.subscription.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;


    /**
     * @param paymentId
     * @return
     */
    public Payment getPaymentById(long paymentId) {
        return paymentRepository.findById(paymentId).get();
    }


    /**
     * createCharge
     *
     * @param amount
     * @return
     */
    public Payment chargeByACustomer(SCustomer customer, double amount) throws StripeException {
        //  Stripe
        Charge charge = null;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("description", "" + customer.getId());
            params.put("amount", (int) amount * 100);
            params.put("currency", "usd");
            params.put("customer", customer.getStripeId());

            charge = Charge.create(params);
            System.out.println(charge);

        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException();
        }

        if (customer == null)
            throw new RuntimeException();
        //  Save Payment Data
        Payment payment = new Payment();
        payment.setPaymentDate(new Date());
        payment.setChargeId(charge.getId());

        payment.setCustomer(customer);

        paymentRepository.save(payment);
        return payment;
    }

    public void refund(String chargeId) {

    }

    public void addCardPayment(CardData cardData) {

    }
}
