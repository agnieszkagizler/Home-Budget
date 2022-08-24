package com.example.homebudgetsda.config;

import com.example.homebudgetsda.dto.RoleEntity;
import com.example.homebudgetsda.repository.UserEntityJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserEntityJpaRepository userEntityJpaRepository;


    @Autowired
    public CustomUserDetailsService(UserEntityJpaRepository userEntityJpaRepository) {
        this.userEntityJpaRepository = userEntityJpaRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityJpaRepository.findByUsername(username)
                .map(userEntity -> User.builder()
                        .username(userEntity.getUsername())
                        .password(userEntity.getPassword())
                        .authorities(userEntity.getRoles().stream()
                                .map(RoleEntity::getRoleName)
                                .map(role -> toString())
                                .collect(Collectors.toList()).toArray(new String[0]))
                        .build()
                ).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
