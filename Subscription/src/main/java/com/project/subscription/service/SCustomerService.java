package com.project.subscription.service;

import com.project.subscription.controller.request.CardData;
import com.project.subscription.models.SCustomer;
import com.project.subscription.repository.CustomerRepository;
import com.project.subscription.repository.PaymentRepository;
import com.project.subscription.util.Mapper;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@Service
public class SCustomerService {

    @Autowired
    CustomerRepository customerRepository;


    @Autowired
    Mapper mapper;
    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * Create Customer
     *
     * @param userId
     * @return
     */
    public Optional<SCustomer> createCustomer(long userId, CardData cardData) {
        Customer customer = null;
        try {

            Map<String, Object> tokenParam = new HashMap<String, Object>();

            Map<String, Object> cardParam = new HashMap<String, Object>(); //add card details
            cardParam.put("number", cardData.getNumber());
            cardParam.put("exp_month", cardData.getExpMonth());
            cardParam.put("exp_year", cardData.getExpYear());
            cardParam.put("cvc", cardData.getCvc());

            tokenParam.put("card", cardParam);

            Token token = Token.create(tokenParam); // create a token

            Map<String, Object> source = new HashMap<String, Object>();
            source.put("source", token.getId()); //add token as source
            source.put("name", "" + userId); //add token as source
            customer = Customer.create(source);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        //  Add to Customer
        SCustomer sCustomer = new SCustomer();
        sCustomer.setUserId(userId);
        sCustomer.setStripeId(customer.getId());
        sCustomer.setPayments(new HashSet<>());
        customerRepository.save(sCustomer);
        return Optional.of(sCustomer);
    }


    /**
     * Get Customer By Id
     *
     * @param id
     * @return
     */
    public SCustomer getCustomerByUserId(long id, CardData cardData) {
        Optional<SCustomer> sCustomer = customerRepository.findByUserId(id);
        if (!sCustomer.isPresent())
            return createCustomer(id, cardData).get();
        return sCustomer.get();
    }

    /**
     * @param id
     * @param cardData
     * @return
     */
    public SCustomer findByUserId(long id) {
        Optional<SCustomer> sCustomer = customerRepository.findByUserId(id);
        return sCustomer.get();
    }

    public SCustomer getCustomerByStripeId(String customerId) {
        Optional<SCustomer> sCustomer = customerRepository.findByStripeId(customerId);
        return sCustomer.get();
    }
}
