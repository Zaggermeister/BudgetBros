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


//    public Optional<User> validateUser(String email, String password) {
//
//        MongoOperations mongoOperations;
//    }
//
//        return  userRepository.findByEmail(email);
//        Query query = new Query();
//
//        query.addCriteria(Criteria.where("Email").is(email).and("Password").is(password));
//
//        return userRepository.findOne(query, User.class);




}
