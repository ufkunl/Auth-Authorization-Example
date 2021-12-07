package com.ufkunl.authandauthorizationtemplate.repository;

import com.ufkunl.authandauthorizationtemplate.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken,String> {
}
