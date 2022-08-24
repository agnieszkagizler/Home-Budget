package com.example.homebudgetsda.repository;

import com.example.homebudgetsda.dto.BudgetCategory;
import com.example.homebudgetsda.dto.BudgetItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeBudgetItemJpaRepository extends JpaRepository<BudgetItemEntity, Long> {

    Optional<BudgetItemEntity> findByCategory(BudgetCategory category);
}
