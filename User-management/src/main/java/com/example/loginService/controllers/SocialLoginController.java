package com.example.loginService.controllers;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.example.loginService.dto.JwtDTO;
import com.example.loginService.dto.Response;
import com.example.loginService.models.Client;
import com.example.loginService.models.ERole;
import com.example.loginService.models.RefreshToken;
import com.example.loginService.models.Role;
import com.example.loginService.security.SecurityUtil;
import com.example.loginService.security.jwt.JwtUtils;
import com.example.loginService.service.ClientService;
import com.example.loginService.service.RefreshTokenService;
import com.example.loginService.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class SocialLoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ClientService clientService;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    JwtUtils jwtUtils;


    /**
     * @param oAuth2AuthenticationToken
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<Response> login(OAuth2AuthenticationToken oAuth2AuthenticationToken) {

        //  Add new user && login || Login if exist
        String userEmail = oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString();
        String username = oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name").toString();

        Response response = clientService.socialLogin(userEmail, username);
        return ResponseEntity.ok(response);
    }
}
