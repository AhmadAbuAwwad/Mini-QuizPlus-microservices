package com.example.loginService.controllers;

import com.example.loginService.controllers.request.AuthRequest;
import com.example.loginService.controllers.request.RefreshTokenRequest;
import com.example.loginService.controllers.request.RegisterRequest;
import com.example.loginService.dto.RefreshTokenDTO;
import com.example.loginService.dto.Response;
import com.example.loginService.exception.RefreshTokenException;
import com.example.loginService.models.Client;
import com.example.loginService.models.ERole;
import com.example.loginService.models.RefreshToken;
import com.example.loginService.models.Role;
import com.example.loginService.security.SecurityUtil;
import com.example.loginService.security.jwt.JwtUtils;
import com.example.loginService.service.ClientService;
import com.example.loginService.service.RefreshTokenService;
import com.example.loginService.service.RoleService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class LoginSignUpController {
    @Autowired
    ClientService clientService;

    @Autowired
    RoleService roleService;

    @Autowired
    JwtUtils jwtTokenUtil;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    RefreshTokenService refreshTokenService;


    /**
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<Response> authenticateUser(@RequestBody AuthRequest loginRequest) {
        Response response = clientService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * @param registerRequest
     * @return
     * @throws AuthenticationException
     */
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws AuthenticationException {
        if (clientService.getByEmail(registerRequest.getEmail()).isPresent()) {
            throw new AuthenticationException("Email is taken");
        }

        // Create new user's account
        Client client = new Client();
        client.setEmail(registerRequest.getEmail());
        client.setUsername(registerRequest.getUsername());
        client.setPassword(securityUtil.passwordEncoder().encode(registerRequest.getPassword()));

        String strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        Role role = roleService.findRole(ERole.valueOf(strRoles));
        roles.add(role);
        client.setRoles(roles);
        clientService.createClient(client);

        return ResponseEntity.ok("You have registered successfully");
    }

    /**
     * @param authHeader
     */
    @PostMapping(value = "/logout")
    public void logout(@RequestHeader(("Authorization")) String authHeader) {
        String email = jwtTokenUtil.getEmailFromJwtToken(authHeader.substring(7));
        clientService.logout(email);
    }


    /**
     * @param request
     * @return
     */
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken).map(RefreshToken::getClient).map(user -> {
            String token = jwtTokenUtil.generateTokenFromEmail(user.getEmail());
            return ResponseEntity.ok(new RefreshTokenDTO(token, requestRefreshToken));
        }).orElseThrow(() -> new RefreshTokenException(requestRefreshToken + "Refresh token is not in database!"));
    }
}
