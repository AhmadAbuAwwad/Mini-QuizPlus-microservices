package com.example.loginService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    BalanceDTO balanceDTO;
    JwtDTO jwtDTO;
}
