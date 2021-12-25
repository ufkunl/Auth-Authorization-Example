package com.ufkunl.authandauthorizationtemplate.service.impl;

import com.ufkunl.authandauthorizationtemplate.dto.request.LoginRequest;
import com.ufkunl.authandauthorizationtemplate.dto.request.RefreshTokenRequest;
import com.ufkunl.authandauthorizationtemplate.dto.response.LoginResponse;
import com.ufkunl.authandauthorizationtemplate.dto.response.TokenRefreshResponse;
import com.ufkunl.authandauthorizationtemplate.entity.AccessToken;
import com.ufkunl.authandauthorizationtemplate.entity.RefreshToken;
import com.ufkunl.authandauthorizationtemplate.entity.User;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.exception.GeneralAppException;
import com.ufkunl.authandauthorizationtemplate.repository.UserRepository;
import com.ufkunl.authandauthorizationtemplate.security.AccessTokenService;
import com.ufkunl.authandauthorizationtemplate.security.JwtUtils;
import com.ufkunl.authandauthorizationtemplate.security.RefreshTokenService;
import com.ufkunl.authandauthorizationtemplate.security.UserDetailsImpl;
import com.ufkunl.authandauthorizationtemplate.service.AuthService;
import com.ufkunl.authandauthorizationtemplate.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AccessTokenService accessTokenService;

    @Autowired
    ResponseUtils responseUtils;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * <p>This method can return token, refresh token, roles
     * it can create Access token and refresh token log. it can control that there is user in db
     * </p>
     * @param loginRequest parameter that have username and password
     * @return generic class that have token, refresh token, roles
     * @since 1.0
     */
    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        User user = userRepository.findById(userDetails.getId()).orElseThrow(()-> new GeneralAppException(RestResponseCode.USERNAME_OR_PASSWORD_NOT_FOUND));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        AccessToken accessToken = accessTokenService.createAccessToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setEmail(userDetails.getEmail());
        loginResponse.setRoles(roles);
        loginResponse.setRefreshToken(refreshToken.getToken());
        loginResponse.setToken(accessToken.getToken());
        loginResponse.setUsername(userDetails.getUsername());
        loginResponse.setId(userDetails.getId());

        return loginResponse;
    }

    /**
     * <p>This method can return token, refresh token, roles by refresh token
     * it can control that there is refresh token in db. it can create Access token and refresh token log.
     * </p>
     * @param refreshTokenRequest parameter that have refresh token
     * @return generic class that have token, refresh token, roles
     * @since 1.0
     */
    @Override
    public TokenRefreshResponse refreshToken(RefreshTokenRequest refreshTokenRequest){

        RefreshToken refreshToken = refreshTokenService.findByTokenAndRevoked(refreshTokenRequest.getRefreshToken(), false)
                .orElseThrow(() -> new GeneralAppException(RestResponseCode.REFRESH_NOT_FOUND));
        refreshToken = refreshTokenService.verifyExpiration(refreshToken);
        User user = refreshToken.getUser();
        AccessToken accessToken = accessTokenService.createAccessToken(user);
        refreshToken = refreshTokenService.createRefreshToken(user.getUserId());
        return new TokenRefreshResponse(accessToken.getToken(), refreshToken.getToken());
    }

}
