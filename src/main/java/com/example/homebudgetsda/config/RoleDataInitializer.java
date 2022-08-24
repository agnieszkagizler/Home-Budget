package com.example.homebudgetsda.config;

import com.example.homebudgetsda.dto.Role;
import com.example.homebudgetsda.dto.RoleEntity;
import com.example.homebudgetsda.dto.UserEntity;
import com.example.homebudgetsda.repository.RoleJpaRepository;
import com.example.homebudgetsda.repository.UserEntityJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleJpaRepository roleJpaRepository;
    private final UserEntityJpaRepository userEntityJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RoleDataInitializer(RoleJpaRepository roleJpaRepository, UserEntityJpaRepository userEntityJpaRepository, PasswordEncoder passwordEncoder) {
        this.roleJpaRepository = roleJpaRepository;
        this.userEntityJpaRepository = userEntityJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (Role role : Role.values()) {
            RoleEntity entity = new RoleEntity();
            entity.setRoleName(role);
            roleJpaRepository.save(entity);
        }
        UserEntity defaultUser = new UserEntity();
        defaultUser.setUsername("ADMIN");
        defaultUser.setPassword(passwordEncoder.encode("12345"));
        defaultUser.setRoles(Collections.singleton(roleJpaRepository.findByRoleName(Role.ADMIN).orElseThrow()));
        userEntityJpaRepository.save(defaultUser);
    }
}
