package com.example.homebudgetsda.repository;

import com.example.homebudgetsda.dto.Role;
import com.example.homebudgetsda.dto.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleEntity,Long> {


    Optional<RoleEntity> findByRoleName(Role roleName);
}
