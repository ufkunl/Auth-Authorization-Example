package com.ufkunl.authandauthorizationtemplate.repository;

import com.ufkunl.authandauthorizationtemplate.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Repository
public interface RoleRepository extends CrudRepository<Role,String> {

    Optional<Role> findByRoleName(String name);

}
