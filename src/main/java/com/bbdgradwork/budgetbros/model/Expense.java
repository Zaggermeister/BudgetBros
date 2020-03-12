package com.bbdgradwork.budgetbros.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("Expenses")
public class Expense {

    @Id
    private String expenseId;

    @Field("CreationId")
    private String creationId;

    @Field("UserId")
    private String userId;

    @Field("ExpenseName")
    private String expenseName;

    @Field("Amount")
    private String amount;

    @Field("Category")
    private String category;

}
