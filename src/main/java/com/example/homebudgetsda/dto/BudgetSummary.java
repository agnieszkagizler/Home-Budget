package com.example.homebudgetsda.dto;

import java.util.List;

public class BudgetSummary {

    private List<BudgetItemEntity> incomes;
    private List<BudgetItemEntity> outcomes;
    private Double balance;

    public List<BudgetItemEntity> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<BudgetItemEntity> incomes) {
        this.incomes = incomes;
    }

    public List<BudgetItemEntity> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<BudgetItemEntity> outcomes) {
        this.outcomes = outcomes;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
