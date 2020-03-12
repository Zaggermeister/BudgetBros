package com.bbdgradwork.budgetbros.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("Budget")
public class Budget {

    @Id
    private String budgetId;

    @Field("UserId")
    private String userId;

    @Field("BudgetIncomeAmount")
    private String budgetIncomeAmount;

    @Field("BudgetPersonalAmount")
    private String budgetPersonalAmount;

    @Field("BudgetHouseholdAmount")
    private String budgetHouseholdAmount;

    @Field("BudgetDeptAmount")
    private String budgetDeptAmount;

    @Field("BudgetOtherAmount")
    private String budgetOtherAmount;

    @Field("SavingsGoal")
    private String savingsGoal;

    @Field("BudgetSavingsAmount")
    private String budgetSavingsAmount;

    @Field("Month")
    private String month;

    @Field("Year")
    private String year;

}
