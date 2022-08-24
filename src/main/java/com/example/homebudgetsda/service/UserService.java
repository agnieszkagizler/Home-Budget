package com.example.homebudgetsda.service;

import com.example.homebudgetsda.dto.Role;
import com.example.homebudgetsda.dto.UserEntity;
import com.example.homebudgetsda.repository.RoleJpaRepository;
import com.example.homebudgetsda.repository.UserEntityJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private UserEntityJpaRepository userEntityJpaRepository;
    private PasswordEncoder passwordEncoder;
    private RoleJpaRepository roleRepository;

    public UserService(UserEntityJpaRepository userEntityJpaRepository, RoleJpaRepository roleRepository) {
        this.userEntityJpaRepository = userEntityJpaRepository;
        this.roleRepository = roleRepository;
    }


    public void createUser(UserEntity newUserEntity) {
        UserEntity user = new UserEntity();
        user.setUsername(newUserEntity.getUsername());
        user.setRoles(Collections.singleton(roleRepository.findByRoleName(Role.STANDARD).orElseThrow()));
        user.setPassword(passwordEncoder.encode(newUserEntity.getPassword()));
        userEntityJpaRepository.save(user);
    }
}
