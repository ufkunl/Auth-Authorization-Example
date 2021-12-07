package com.ufkunl.authandauthorizationtemplate.security;


import com.ufkunl.authandauthorizationtemplate.entity.RefreshToken;
import com.ufkunl.authandauthorizationtemplate.entity.User;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.exception.GeneralAppException;
import com.ufkunl.authandauthorizationtemplate.repository.RefreshTokenRepository;
import com.ufkunl.authandauthorizationtemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
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

    /**
     * <p>This method control that there is user in db.
     * throw exception if user not found. it is return refresh token.
     * </p>
     * @param userId parameter
     * @return refresh token
     * @since 1.0
     */
    public RefreshToken createRefreshToken(String userId) {
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

    /**
     * <p>This method control that refresh token's expire date.
     * it is revoke and throw exception if refresh token expire. it is return refresh token.
     * </p>
     * @param refreshToken parameter
     * @return refresh token
     * @since 1.0
     */
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
            throw new GeneralAppException(RestResponseCode.REFRESH_NOT_FOUND);
        }
        return refreshToken;
    }

}
