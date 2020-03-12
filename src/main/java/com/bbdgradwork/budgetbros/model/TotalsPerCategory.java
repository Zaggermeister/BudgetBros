package com.bbdgradwork.budgetbros.model;

import lombok.Data;

@Data
public class TotalsPerCategory {
    private float personal;
    private float household;
    private float debt;
    private float other;
}
