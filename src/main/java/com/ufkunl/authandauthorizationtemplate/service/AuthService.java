package com.ufkunl.authandauthorizationtemplate.service;

import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import com.ufkunl.authandauthorizationtemplate.dto.request.LoginRequest;
import com.ufkunl.authandauthorizationtemplate.dto.request.RefreshTokenRequest;
import com.ufkunl.authandauthorizationtemplate.dto.request.RegisterRequest;
import com.ufkunl.authandauthorizationtemplate.dto.response.LoginResponse;
import com.ufkunl.authandauthorizationtemplate.dto.response.TokenRefreshResponse;
import com.ufkunl.authandauthorizationtemplate.entity.AccessToken;
import com.ufkunl.authandauthorizationtemplate.entity.RefreshToken;
import com.ufkunl.authandauthorizationtemplate.entity.User;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.exception.GeneralAppException;
import com.ufkunl.authandauthorizationtemplate.repository.RoleRepository;
import com.ufkunl.authandauthorizationtemplate.repository.UserRepository;
import com.ufkunl.authandauthorizationtemplate.security.AccessTokenService;
import com.ufkunl.authandauthorizationtemplate.security.RefreshTokenService;
import com.ufkunl.authandauthorizationtemplate.security.UserDetailsImpl;
import com.ufkunl.authandauthorizationtemplate.util.ResponseUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Service
public class AuthService{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AccessTokenService accessTokenService;

    @Autowired
    ResponseUtils responseUtils;


    /**
     * <p>This method can return token, refresh token, roles
     * it can create Access token and refresh token log. it can control that there is user in db
     * </p>
     * @param loginRequest parameter that have username and password
     * @return generic class that have token, refresh token, roles
     * @since 1.0
     */
    public ResponseEntity<RestResponse> authenticate(LoginRequest loginRequest) {

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

        return ResponseEntity.ok(responseUtils.createResponse(loginResponse, RestResponseCode.SUCCESS));
    }


    /**
     * <p>This method can return token, refresh token, roles by refresh token
     * it can control that there is refresh token in db. it can create Access token and refresh token log.
     * </p>
     * @param refreshTokenRequest parameter that have refresh token
     * @return generic class that have token, refresh token, roles
     * @since 1.0
     */
    public ResponseEntity<RestResponse> refreshToken(RefreshTokenRequest refreshTokenRequest){

        RefreshToken refreshToken = refreshTokenService.findByTokenAndRevoked(refreshTokenRequest.getRefreshToken(), false)
                .orElseThrow(() -> new GeneralAppException(RestResponseCode.REFRESH_NOT_FOUND));
        refreshToken = refreshTokenService.verifyExpiration(refreshToken);
        User user = refreshToken.getUser();
        AccessToken accessToken = accessTokenService.createAccessToken(user);
        refreshToken = refreshTokenService.createRefreshToken(user.getUserId());
        TokenRefreshResponse tokenRefreshResponse = new TokenRefreshResponse(accessToken.getToken(), refreshToken.getToken());

        return ResponseEntity.ok(responseUtils.createResponse(tokenRefreshResponse,RestResponseCode.SUCCESS));
    }

    /**
     * <p>This method can return created user
     * it can control that there is same user in db. it can encode user password.
     * </p>
     * @param registerRequest parameter that have user info
     * @return created user
     * @since 1.0
     */
    public ResponseEntity<RestResponse> registerUser(RegisterRequest registerRequest){
        boolean userExist = userRepository.existsByUserNameOrEmail(registerRequest.getUsername(),registerRequest.getEmail());
        if (userExist) {
            throw new GeneralAppException(RestResponseCode.USERNAME_OR_EMAIL_EXIST);
        }
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user = userRepository.save(user);

        return ResponseEntity.ok(responseUtils.createResponse(user,RestResponseCode.SUCCESS));
    }

}
