package com.ufkunl.authandauthorizationtemplate.util;

import com.ufkunl.authandauthorizationtemplate.entity.User;
import com.ufkunl.authandauthorizationtemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class UserUtils {

    @Autowired
    UserRepository userRepository;

    public User getAuthenticatedUser(){
        return userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(()->new EntityNotFoundException("Authenticated user not found"));
    }

    public String getAuthenticatedPersonalId() throws EntityNotFoundException {
        return getAuthenticatedUser().getUserId();
    }

//    public PersonalDTO getAuthenticatedPersonalDTO() throws EntityNotFoundException {
//        return personalMapper.toDTO(getAuthenticatedPersonal());
//    }

}
