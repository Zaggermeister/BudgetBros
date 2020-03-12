package com.bbdgradwork.budgetbros.services;

import com.bbdgradwork.budgetbros.model.Budget;
import com.bbdgradwork.budgetbros.model.Expense;
import com.bbdgradwork.budgetbros.model.TotalsPerCategory;
import com.bbdgradwork.budgetbros.model.User;
import com.bbdgradwork.budgetbros.repository.ExpenseRepository;
import com.bbdgradwork.budgetbros.repository.UserRepository;
import org.apache.catalina.valves.ErrorReportValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public String calculateSavings (Budget budget) {
        float totalExpense = Float.parseFloat(budget.getBudgetHouseholdAmount())
                + Float.parseFloat(budget.getBudgetDeptAmount())
                + Float.parseFloat(budget.getBudgetPersonalAmount())
                + Float.parseFloat(budget.getBudgetOtherAmount());
        return String.valueOf(Float.parseFloat(budget.getBudgetIncomeAmount()) - totalExpense);
    }

    public Optional<User> getUser(String userId) {
        return userRepository.findById(userId);
}

    public User validateUser(String email, String password) {

        try {
            User user = userRepository.findByEmail(email);

            if (password == null || password.equals("") ||user == null) {
                return null;
            }

            user.getPassword().equals(password);
            user.setActive(true);
            userRepository.save(user);
            return user;
        }  catch(Error e) {
            System.out.println(e.getMessage());
            return null;
        }



    }

    public TotalsPerCategory getTotalExpense(List<Expense> expenses) {
        TotalsPerCategory totalExpensePerCategory = new TotalsPerCategory();
        for (int i = 0; i < expenses.size(); i++) {
            if(expenses.get(i).getCategory().equals("Household"))
                totalExpensePerCategory.setHousehold(totalExpensePerCategory.getHousehold() + Float.parseFloat(expenses.get(i).getAmount()));
            if(expenses.get(i).getCategory().equals("Personal"))
                totalExpensePerCategory.setPersonal(totalExpensePerCategory.getPersonal() + Float.parseFloat(expenses.get(i).getAmount()));
            if(expenses.get(i).getCategory().equals("Debt"))
                totalExpensePerCategory.setDebt(totalExpensePerCategory.getDebt() + Float.parseFloat(expenses.get(i).getAmount()));
            if(expenses.get(i).getCategory().equals("Other"))
                totalExpensePerCategory.setOther(totalExpensePerCategory.getOther() + Float.parseFloat(expenses.get(i).getAmount()));
        }
        return totalExpensePerCategory;
    }


    public TotalsPerCategory getTotalBudgetLeft(List<Expense> expenses, Budget budget) {

        TotalsPerCategory totalExpensePerCategory = getTotalExpense(expenses);
        TotalsPerCategory totalBudgetLeftPerCat = new TotalsPerCategory();

        totalBudgetLeftPerCat.setPersonal(Float.parseFloat(budget.getBudgetPersonalAmount()) - totalExpensePerCategory.getPersonal());
        totalBudgetLeftPerCat.setHousehold(Float.parseFloat(budget.getBudgetHouseholdAmount()) - totalExpensePerCategory.getHousehold());
        totalBudgetLeftPerCat.setDebt(Float.parseFloat(budget.getBudgetDeptAmount()) - totalExpensePerCategory.getDebt());
        totalBudgetLeftPerCat.setOther(Float.parseFloat(budget.getBudgetOtherAmount()) - totalExpensePerCategory.getOther());

        return totalBudgetLeftPerCat;
    }




}
