package com.bbdgradwork.budgetbros.repository;

import com.bbdgradwork.budgetbros.model.Expense;
import com.bbdgradwork.budgetbros.model.UserExpense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByCategory(String Category);
    List<Expense> findByUserId(String userId);

    String deleteByUserIdAndCreationId(String userId, String creationId);
}
