package com.ufkunl.authandauthorizationtemplate.security;


import com.ufkunl.authandauthorizationtemplate.entity.RefreshToken;
import com.ufkunl.authandauthorizationtemplate.entity.User;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.exception.GeneralAppException;
import com.ufkunl.authandauthorizationtemplate.repository.RefreshTokenRepository;
import com.ufkunl.authandauthorizationtemplate.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByTokenAndRevoked(String token,boolean revoked) {
        return refreshTokenRepository.findByTokenAndRevoked(token,revoked);
    }

    public RefreshToken createRefreshToken(String userId) throws NotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new GeneralAppException(RestResponseCode.USERNAME_OR_PASSWORD_NOT_FOUND);
        }
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user.get());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenDurationMs / 1000));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setRevoked(false);
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
            throw new GeneralAppException(RestResponseCode.REFRESH_NOT_FOUND);
        }
        return token;
    }

}
