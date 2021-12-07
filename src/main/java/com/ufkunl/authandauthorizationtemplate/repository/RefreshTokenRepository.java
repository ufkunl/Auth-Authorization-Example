package com.ufkunl.authandauthorizationtemplate.repository;

import com.ufkunl.authandauthorizationtemplate.entity.RefreshToken;
import com.ufkunl.authandauthorizationtemplate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByTokenAndRevoked(String token, boolean revoked);

    @Modifying
    int deleteByUser(User user);

}
