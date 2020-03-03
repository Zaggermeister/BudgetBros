package com.bbdgradwork.budgetbros.services;

import com.bbdgradwork.budgetbros.model.Expense;
import com.bbdgradwork.budgetbros.model.User;
import com.bbdgradwork.budgetbros.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetBrosService {

    @Autowired
    Repository repository;

    public BudgetBrosService() {

    }

    //TODO Complete function
    public boolean addExpense(Expense expense) {
        if(expense.getCategory().equals("category1")) {
            return true;
        }
        return false;
    }

    public boolean addUser(User user) {
        try {
            repository.save(user);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public Optional<User> getUser(String userId) {
        return repository.findById(userId);
    }


}
