package com.example.loginService.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    WebClient webClient;


    /**
     *
     */
    public WebClientService() {
        this.webClient = WebClient.create();
    }


    /**
     * @param jwt
     * @param uri
     * @param typeReference
     * @param <T>
     * @return
     */
    public <T> Mono<T> getResponse(String jwt, String uri,
                                   ParameterizedTypeReference<T> typeReference) {
        try {
            return (Mono<T>) webClient
                    .get()
                    .uri(uri)
                    .header("Authorization", "Bearer " + jwt)
                    .retrieve()
                    .bodyToMono(typeReference);
        } catch (Exception e) {
            return null;
        }
    }
}
