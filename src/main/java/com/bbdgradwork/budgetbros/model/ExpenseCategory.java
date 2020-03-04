package com.bbdgradwork.budgetbros.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "ExpenseCategory")
public class ExpenseCategory {
    @Id
    private String userId;

    @Field("Name")
    private String name;

    @Field("Surname")
    private String surname;

    @Field("Email")
    private String email;

    @Field("Income")
    private String income;
}
