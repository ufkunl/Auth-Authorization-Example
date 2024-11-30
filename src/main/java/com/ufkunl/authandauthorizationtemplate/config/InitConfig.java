package com.ufkunl.authandauthorizationtemplate.config;

import com.ufkunl.authandauthorizationtemplate.entity.Role;
import com.ufkunl.authandauthorizationtemplate.entity.User;
import com.ufkunl.authandauthorizationtemplate.repository.RoleRepository;
import com.ufkunl.authandauthorizationtemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class InitConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public InitConfig(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    private void createSuperAdminUser() {
        boolean userExist = userRepository.existsByUserName("admin");
        if (!userExist) {
            Role role = new Role();
            role.setRoleName("SUPER-ADMIN");
            roleRepository.save(role);

            User user = new User();
            user.setUserName("admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(encoder.encode("12345"));
            user.setRoles(Collections.singleton(role));
            userRepository.save(user);
        }
    }
}
