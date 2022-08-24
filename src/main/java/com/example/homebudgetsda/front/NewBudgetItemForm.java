package com.example.homebudgetsda.front;

import org.hibernate.validator.constraints.Length;

public class NewBudgetItemForm {

    @Length(min = 5)
    private double amount;

    public NewBudgetItemForm() {
    }

    public double getAmount() {
        return amount;
    }
}
