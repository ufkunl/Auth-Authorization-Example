package com.ufkunl.authandauthorizationtemplate.security;

import com.ufkunl.authandauthorizationtemplate.entity.AccessToken;
import com.ufkunl.authandauthorizationtemplate.entity.User;
import com.ufkunl.authandauthorizationtemplate.repository.AccessTokenRepository;
import com.ufkunl.authandauthorizationtemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
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

    /**
     * <p>This method can create new token by username.
     * </p>
     * @param user parameter
     * @return access token
     * @since 1.0
     */
    public AccessToken createAccessToken(User user) {
        String jwt = jwtUtils.generateTokenFromUsername(user.getUserName());

        AccessToken accessToken = new AccessToken();
        accessToken.setUser(user);
        accessToken.setExpiryDate(LocalDateTime.now().plusSeconds(accessTokenDurationMs / 1000));
        accessToken.setToken(jwt);
        accessToken = accessTokenRepository.save(accessToken);
        return accessToken;
    }
}
