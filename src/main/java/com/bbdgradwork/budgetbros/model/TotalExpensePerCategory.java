package com.bbdgradwork.budgetbros.model;

import lombok.Data;

@Data
public class TotalExpensePerCategory {
    private float personal;
    private float household;
    private float dept;
    private float other;
}
