package com.example.homebudgetsda.repository;

import com.example.homebudgetsda.dto.BudgetEntity;
import com.example.homebudgetsda.dto.BudgetItemEntity;
import com.example.homebudgetsda.dto.BudgetItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeBudgetJpaRepository extends JpaRepository<BudgetEntity, Long> {


}
