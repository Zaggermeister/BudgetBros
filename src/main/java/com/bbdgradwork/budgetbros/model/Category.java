package com.bbdgradwork.budgetbros.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("Expense_Categories")
public class Category {

    @Field("ExpenseName")
    private String expenseName;

    @Field("ExpenseDescription")
    private String expenseDescription;
}
