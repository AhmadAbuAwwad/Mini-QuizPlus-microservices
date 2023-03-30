package com.example.loginService.service;

import com.example.loginService.exception.RefreshTokenException;
import com.example.loginService.models.RefreshToken;
import com.example.loginService.repository.ClientRepository;
import com.example.loginService.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${RefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private ClientRepository clientRepository;


    /**
     * @param token
     * @return
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * @param clientId
     * @return
     */
    public RefreshToken createRefreshToken(Long clientId) {
        RefreshToken refreshToken = new RefreshToken();


        refreshToken.setClient(clientRepository.findById(clientId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    /**
     * @param token
     * @return
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getToken() + "Refresh token expired. Please make a new signin request");
        }

        return token;
    }
}
