package com.project.subscription.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CardData {
    String number;
    String expMonth;
    String expYear;
    String cvc;
}
