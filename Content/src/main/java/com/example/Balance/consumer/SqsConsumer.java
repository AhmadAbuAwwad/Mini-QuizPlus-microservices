package com.example.Balance.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsConsumer {

    @Autowired
    private MessageHandler messageHandler;


    /**
     * @param message
     */
    @SqsListener(value = "https://sqs.eu-central-1.amazonaws.com/221142158129/balance_queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(String message) {
        Message message1 = new Message();
        try {
            SqsMessage sqsMessage = (new ObjectMapper()).readValue(message, SqsMessage.class);
            message1 = (new ObjectMapper()).readValue(sqsMessage.getBody(), Message.class);

            messageHandler.handle(message1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println(message);
    }
}