package com.bbdgradwork.budgetbros.repository;

import com.bbdgradwork.budgetbros.model.UserExpense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserExpenseRepository extends MongoRepository<UserExpense, String> {
    List<UserExpense> findByCustomerId(String customerId);
}
