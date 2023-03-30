package com.example.Balance.consumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("code")
    private MessageCode code;

    @JsonProperty("plan")
    private EPlan planType;
}
