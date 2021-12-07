package com.ufkunl.authandauthorizationtemplate.repository;

import com.ufkunl.authandauthorizationtemplate.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Repository
public interface UserRepository extends CrudRepository<User,String> {

    Optional<User> findByUserName(String username);

    boolean existsByUserNameOrEmail(String userName, String email);

}
