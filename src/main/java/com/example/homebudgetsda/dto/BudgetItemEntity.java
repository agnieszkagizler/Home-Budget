package com.example.homebudgetsda.dto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget_item")
public class BudgetItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time")
    private LocalDateTime time;

    @OneToOne
    private CategoryEntity categoryId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private BudgetEntityType type;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private BudgetCategory category;

    public BudgetItemEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public CategoryEntity getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryEntity categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BudgetEntityType getType() {
        return type;
    }

    public void setType(BudgetEntityType type) {
        this.type = type;
    }

    public BudgetCategory getCategory() {
        return category;
    }

    public void setCategory(BudgetCategory category) {
        this.category = category;
    }
}