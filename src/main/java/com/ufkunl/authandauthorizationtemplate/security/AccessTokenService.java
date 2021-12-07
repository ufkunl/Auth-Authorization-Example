package com.ufkunl.authandauthorizationtemplate.security;

import com.ufkunl.authandauthorizationtemplate.entity.AccessToken;
import com.ufkunl.authandauthorizationtemplate.entity.User;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.exception.GeneralAppException;
import com.ufkunl.authandauthorizationtemplate.repository.AccessTokenRepository;
import com.ufkunl.authandauthorizationtemplate.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccessTokenService {

    @Value("${jwtExpirationMs}")
    private Long accessTokenDurationMs;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    JwtUtils jwtUtils;

    public AccessToken createAccessToken(UserDetailsImpl userDetails) throws NotFoundException {
        String jwt = jwtUtils.generateJwtToken(userDetails);
        User user = userRepository.findById(userDetails.getId()).orElseThrow(()-> new GeneralAppException(RestResponseCode.USERNAME_OR_PASSWORD_NOT_FOUND));

        AccessToken accessToken = new AccessToken();
        accessToken.setUser(user);
        accessToken.setExpiryDate(LocalDateTime.now().plusSeconds(accessTokenDurationMs / 1000));
        accessToken.setToken(jwt);
        accessToken = accessTokenRepository.save(accessToken);
        return accessToken;
    }

    public AccessToken createAccessTokenFromRefreshToken(User user) {
        String jwt = jwtUtils.generateTokenFromUsername(user.getUserName());

        AccessToken accessToken = new AccessToken();
        accessToken.setUser(user);
        accessToken.setExpiryDate(LocalDateTime.now().plusSeconds(accessTokenDurationMs / 1000));
        accessToken.setToken(jwt);
        accessToken = accessTokenRepository.save(accessToken);
        return accessToken;
    }

}
