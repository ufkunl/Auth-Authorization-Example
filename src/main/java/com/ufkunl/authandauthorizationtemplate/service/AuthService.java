package com.ufkunl.authandauthorizationtemplate.service;

import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import com.ufkunl.authandauthorizationtemplate.dto.request.LoginRequest;
import com.ufkunl.authandauthorizationtemplate.dto.request.RefreshTokenRequest;
import com.ufkunl.authandauthorizationtemplate.dto.request.SignupRequest;
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

    public ResponseEntity<RestResponse> authenticate(LoginRequest loginRequest) throws NotFoundException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        AccessToken accessToken = accessTokenService.createAccessToken(userDetails);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setEmail(userDetails.getEmail());
        loginResponse.setRoles(roles);
        loginResponse.setRefreshToken(refreshToken.getToken());
        loginResponse.setToken(accessToken.getToken());
        loginResponse.setUsername(userDetails.getUsername());
        loginResponse.setId(userDetails.getId());

        return ResponseEntity.ok(responseUtils.createResponse(loginResponse, RestResponseCode.SUCCESS));
    }

    public ResponseEntity<RestResponse> refreshToken(RefreshTokenRequest request) throws NotFoundException {

        RefreshToken refreshToken = refreshTokenService.findByTokenAndRevoked(request.getRefreshToken(), false)
                .orElseThrow(() -> new GeneralAppException(RestResponseCode.REFRESH_NOT_FOUND));
        refreshToken = refreshTokenService.verifyExpiration(refreshToken);
        User user = refreshToken.getUser();
        AccessToken accessToken = accessTokenService.createAccessTokenFromRefreshToken(user);
        refreshToken = refreshTokenService.createRefreshToken(user.getUserId());
        TokenRefreshResponse tokenRefreshResponse = new TokenRefreshResponse(accessToken.getToken(), refreshToken.getToken());

        return ResponseEntity.ok(responseUtils.createResponse(tokenRefreshResponse,RestResponseCode.SUCCESS));
    }

    public ResponseEntity<RestResponse> registerUser(SignupRequest signUpRequest){
        boolean userExist = userRepository.existsByUserNameOrEmail(signUpRequest.getUsername(),signUpRequest.getEmail());
        if (userExist) {
            throw new GeneralAppException(RestResponseCode.USERNAME_OR_EMAIL_EXIST);
        }
        User user = new User();
        user.setUserName(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user = userRepository.save(user);

        return ResponseEntity.ok(responseUtils.createResponse(user,RestResponseCode.SUCCESS));
    }

}
