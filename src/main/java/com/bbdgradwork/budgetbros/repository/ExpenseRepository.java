package com.bbdgradwork.budgetbros.repository;

import com.bbdgradwork.budgetbros.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ExpenseRepository extends MongoRepository<Expense, String> {

}
