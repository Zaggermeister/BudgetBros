package com.bbdgradwork.budgetbros.model;

import com.fasterxml.jackson.annotation.JsonProperty;

//TODO Complete class
public class Expense {
    private String expenseName;
    private String expenseDescription;
    private String category;

    public Expense(@JsonProperty("name") String expenseName,
                   @JsonProperty("description") String expenseDescription,
                   @JsonProperty("category") String category) {
        this.expenseName = expenseName;
        this.expenseDescription = expenseDescription;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public String getExpenseName() {
        return expenseName;
    }
}
