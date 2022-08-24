package com.example.homebudgetsda.dto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "budget")
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private UserEntity userId;

    @OneToMany
    private List<BudgetItemEntity> budgetEntities;

    public BudgetEntity() {

    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public List<BudgetItemEntity> getBudgetEntities() {
        return budgetEntities;
    }

    public void setBudgetEntities(List<BudgetItemEntity> budgetEntities) {
        this.budgetEntities = budgetEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
