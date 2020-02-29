package com.bbdgradwork.budgetbros.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BudgetBrosRestController {
    @GetMapping("/mybudget")
    public String getBudget(@RequestParam(name="name", required=false, defaultValue="World") String name) {
        return "{cheese: R5000, bread: R10}";
    }
}
