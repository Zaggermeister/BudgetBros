package com.bbdgradwork.budgetbros.repository;

import com.bbdgradwork.budgetbros.model.Category;
import com.bbdgradwork.budgetbros.model.Expense;
import com.bbdgradwork.budgetbros.model.UserExpense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {

}