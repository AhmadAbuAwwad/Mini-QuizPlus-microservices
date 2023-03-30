package com.example.Balance.consumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SqsMessage {

    @JsonProperty("MessageId")
    private String messageId;

    @JsonProperty("Subject")
    private String subject;

    @JsonProperty("Message")
    private String body;
}
