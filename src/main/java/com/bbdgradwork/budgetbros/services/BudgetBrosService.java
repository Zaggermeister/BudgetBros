package com.bbdgradwork.budgetbros.services;

import com.bbdgradwork.budgetbros.model.Expense;
import com.bbdgradwork.budgetbros.model.UserLogin;
import com.bbdgradwork.budgetbros.repository.ExpenseRepository;
import com.bbdgradwork.budgetbros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetBrosService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    public BudgetBrosService() {

    }


    public boolean addExpense(Expense expense) {
        try {
            expenseRepository.save(expense);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean addUser(UserLogin user) {
        try {
            userRepository.save(user);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public Optional<UserLogin> getUser(String userId) {
        return userRepository.findById(userId);
    }


}
