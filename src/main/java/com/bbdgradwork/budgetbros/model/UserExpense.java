package com.bbdgradwork.budgetbros.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "UserExpense")
public class UserExpense {

    @Id
    private String userExpenseId;

    @Field("CustomerId")
    private String customerId;

    @Field("ExpenseId")
    private String expenseId;

    @Field("AmountSpent")
    private String amountSpent;

    @Field("Month")
    private String month;

    @Field("Year")
    private String year;

}
