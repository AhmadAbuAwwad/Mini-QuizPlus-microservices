package com.project.subscription.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.subscription.models.EPlan;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Message {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("code")
    private MessageCode code;

    @JsonProperty("plan")
    private EPlan planType;

    public Message(Long userId, MessageCode code) {
        this.userId = userId;
        this.code = code;
    }
}
