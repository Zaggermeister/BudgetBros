package com.bbdgradwork.budgetbros.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//TODO Complete class
@Data
@Document("Expenses")
public class Expense {

    @Field("ExpenseId")
    private String expenseId;

    @Field("ExpenseName")
    private String expenseName;

    @Field("ExpenseDescription")
    private String expenseDescription;

    @Field("Category")
    private String category;

}
