package com.example.loginService.service;

import com.example.loginService.controllers.request.AuthRequest;
import com.example.loginService.dto.BalanceDTO;
import com.example.loginService.dto.JwtDTO;
import com.example.loginService.dto.Response;
import com.example.loginService.models.Client;
import com.example.loginService.models.ERole;
import com.example.loginService.models.RefreshToken;
import com.example.loginService.models.Role;
import com.example.loginService.repository.ClientRepository;
import com.example.loginService.security.jwt.JwtUtils;
import com.example.loginService.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;


    @Autowired
    WebClientService webClientService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleService roleService;


    /**
     * @param userEmail
     * @param username
     * @return
     */
    public Response socialLogin(String userEmail, String username) {

        //  Finding User
        Optional<Client> clientOptional = clientRepository.findByEmail(userEmail);

        // Create new client's if Not exist
        if (!clientOptional.isPresent()) {
            Client client = new Client();
            client.setEmail(userEmail);
            client.setUsername(username);

            Set<Role> roles = new HashSet<>();
            Role role = roleService.findRole(ERole.ROLE_CLIENT);
            roles.add(role);
            client.setRoles(roles);

            clientOptional = Optional.of(clientRepository.save(client));
        }

        //  Generate token
        String jwt = jwtUtils.generateJwtToken(userEmail);

        //  Generate Referesh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(clientOptional.get().getId());

        //  Set Roles
        List<String> roles = clientOptional.get().getRoles().stream().map(item -> item.getRoleName().toString())
                .collect(Collectors.toList());
        ParameterizedTypeReference<BalanceDTO> typeReference = new ParameterizedTypeReference<>() {
        };


        //  Async Api Call Using Web Client
        Mono<BalanceDTO> balance = webClientService.getResponse(jwt,
                "http://localhost:8082/balance/getBalanceByUserId/" + clientRepository.
                        findByEmail(userEmail).get().getId(), typeReference);

        return new Response(balance.block(),
                new JwtDTO(jwt, refreshToken.getToken(), clientOptional.get().getId(),
                        clientOptional.get().getUsername(), clientOptional.get().getEmail(), roles));
    }

    /**
     * @param loginRequest
     * @return
     */
    public Response login(AuthRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails.getEmail());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        ParameterizedTypeReference<BalanceDTO> typeReference = new ParameterizedTypeReference<>() {
        };


        //  Async Api Call Using Web Client
        Mono<BalanceDTO> balance = webClientService.getResponse(jwt,
                "http://localhost:8082/balance/getBalanceByUserId/" + clientRepository.
                        findByEmail(loginRequest.getEmail()).get().getId(), typeReference);

        return new Response(balance.block(),
                new JwtDTO(jwt, refreshToken.getToken(), userDetails.getId(),
                        userDetails.getUsername(), userDetails.getEmail(), roles));
    }


    /**
     * @return
     */
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    /**
     * @param email
     * @return
     */
    public Optional<Client> getByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    /**
     * @param client
     * @return
     */
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    /**
     * @param email
     */
    public void logout(String email) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        Client client = clientOptional.get();
        client.setLastLogout(new Date());
        clientRepository.save(client);
    }

}
