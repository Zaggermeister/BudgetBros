package com.bbdgradwork.budgetbros.services;

import com.bbdgradwork.budgetbros.model.Expense;
import com.bbdgradwork.budgetbros.model.User;
import com.bbdgradwork.budgetbros.repository.ExpenseRepository;
import com.bbdgradwork.budgetbros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetBrosService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    MongoOperations mongoOperations;
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

    public boolean addUser(User user) {
        try {
            userRepository.save(user);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public Optional<User> getUser(String userId) {
        return userRepository.findById(userId);
}

    public User validateUser(String email, String password) {

        User user = userRepository.findByEmail(email);
        System.out.println(user);
        if(user.getPassword().equals(password)){
            System.out.println("YEAH");
            user.setActive(true);
            userRepository.save(user);
            return user;
        }
        return null;


    }


}
