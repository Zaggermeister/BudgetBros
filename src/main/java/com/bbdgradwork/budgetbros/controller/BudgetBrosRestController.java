package com.bbdgradwork.budgetbros.controller;

import com.bbdgradwork.budgetbros.model.*;
import com.bbdgradwork.budgetbros.repository.BudgetRepository;
import com.bbdgradwork.budgetbros.repository.ExpenseRepository;
import com.bbdgradwork.budgetbros.repository.UserExpenseRepository;
import com.bbdgradwork.budgetbros.repository.UserRepository;
import com.bbdgradwork.budgetbros.services.BudgetBrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1")
@RestController
public class BudgetBrosRestController {

    private final BudgetBrosService budgetBrosService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserExpenseRepository userExpenseRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    public BudgetBrosRestController(BudgetBrosService budgetBrosService) {
        this.budgetBrosService = budgetBrosService;
    }

    @GetMapping("/mybudget")
    public String getBudget(@RequestParam(name="name", required=false, defaultValue="World") String name) {
        return "{cheese: R5000, bread: R10}";
    }

    @PostMapping("/expense")
    public ResponseEntity<TotalsPerCategory> addExpenses(@RequestBody Expense expense) {

        if(budgetBrosService.addExpense(expense)) {
            List<Expense> expenses = expenseRepository.findByUserId(expense.getUserId());
            TotalsPerCategory totalsPerCategory = budgetBrosService.getTotalExpense(expenses);
            return ResponseEntity.status(201).body(totalsPerCategory);
        }
        TotalsPerCategory totalsPerCategory = new TotalsPerCategory();
        return ResponseEntity.status(400).body(totalsPerCategory);
    }

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        if (budgetBrosService.addUser(user)) {
            return ResponseEntity.status(201).body("Success");
        }
        return ResponseEntity.status(400).body("Failed");

    }


    // Edit the password of a user
    @PutMapping("/user/{userId}")
    public ResponseEntity<String> editUser(@RequestBody User user, @PathVariable("userId") String userId) {
        User prevUser = budgetBrosService.getUser(userId).get();
        prevUser.setPassword(user.getPassword());
        userRepository.save(prevUser);
        return ResponseEntity.status(200).body("Success");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(200).body(userRepository.findAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable("userId") String userId) {
        Optional<User> result = budgetBrosService.getUser(userId);
        if(result.isPresent())
            return ResponseEntity.status(200).body(result);
        else
            return ResponseEntity.status(404).body(result);

    }

    // Get all the expenses
    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.status(200).body(expenseRepository.findAll());
    }

    // Get all the expenses for a specific user ID
//    @GetMapping("/expenses/{category}")
//    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable("category") String category) {
//        return ResponseEntity.status(200).body(expenseRepository.findByCategory(category));
//    }


    // Get all the user expenses
    @GetMapping("/userExpenses")
    public ResponseEntity<List<UserExpense>> getUserExpenses() {
        return ResponseEntity.status(200).body(userExpenseRepository.findAll());
    }

    // Return all the user expenses for a specific user ID
    @GetMapping("/userExpenses/{userId}")
    public ResponseEntity<List<UserExpense>> getUserExpensesByUserID(@PathVariable("userId") String userId) {
        return ResponseEntity.status(200).body(userExpenseRepository.findByCustomerId(userId));
    }

    // Add a new user expense
    @PostMapping("/userExpense")
    public ResponseEntity<String> addUserExpense(@RequestBody UserExpense userExpense) {
        userExpenseRepository.save(userExpense);
        return ResponseEntity.status(201).body("Success");
    }

    // Delete user expense according to userExpenseId
    @DeleteMapping("/userExpense/{userExpenseId}")
    public ResponseEntity<List<UserExpense>> deleteUserExpense(@PathVariable("userExpenseId") String userExpenseId) {
        userExpenseRepository.deleteById(userExpenseId);
        return ResponseEntity.status(200).body(userExpenseRepository.findAll());
    }

    @DeleteMapping("/expense/{expenseId}")
    public ResponseEntity<List<Expense>> deleteExpense(@PathVariable("expenseId") String expenseId) {
        expenseRepository.deleteById(expenseId);
        return ResponseEntity.status(200).body(expenseRepository.findAll());
    }


    // Check login and return userId
    //TODO Complete this function
    @PostMapping("/login")
    public ResponseEntity<String> deleteUserExpense(@RequestParam("email") String email, @RequestParam("password") String password) {
        User user = userRepository.findByEmail(email);
        return ResponseEntity.status(200).body("");
    }

    // Post a new budget
    @PostMapping("/budget")
    public ResponseEntity<String> addBudget(@RequestBody Budget budget) {
        budget.setBudgetSavingsAmount(budgetBrosService.calculateSavings(budget));
        budgetRepository.save(budget);
        return ResponseEntity.status(200).body(budget.getBudgetSavingsAmount());
    }

    // Get all the budgets
    @GetMapping("/budget")
    public ResponseEntity<List<Budget>> getAllBudgets() {
        return ResponseEntity.status(200).body(budgetRepository.findAll());
    }

    // Delete the budget of a specific user
    @DeleteMapping("/budget/{budgetId}")
    public ResponseEntity<List<Budget>> deleteBudget(@PathVariable("budgetId") String budgetId) {
        budgetRepository.deleteById(budgetId);
        return ResponseEntity.status(200).body(budgetRepository.findAll());
    }

    // Get the budget of a specific user
    @GetMapping("/budget/{userId}")
    public ResponseEntity<Budget> getBudgetByUserId(@PathVariable("userId") String userId) {
        return ResponseEntity.status(200).body(budgetRepository.findByUserId(userId));
    }


    // Get expenses for user
    @GetMapping("/expenses/{userId}")
    public ResponseEntity<List<Expense>> getExpenseByUser(@PathVariable("userId") String userId) {
        return ResponseEntity.status(200).body(expenseRepository.findByUserId(userId));
    }


    // Get total expenses for each category
    @GetMapping("/totalExpensesPerCategory/{userId}")
    public ResponseEntity<TotalsPerCategory> getTotalExpensesPerCategory(@PathVariable("userId") String userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        TotalsPerCategory totalExpensePerCategory = budgetBrosService.getTotalExpense(expenses);

        return ResponseEntity.status(200).body(totalExpensePerCategory);
    }


    // Get total budget left per category per user
    @GetMapping("/totalBudgetLeftPerCategory/{userId}")
    public ResponseEntity<TotalsPerCategory> getTotalBudgetLeftPerCategory(@PathVariable("userId") String userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        Budget budget = budgetRepository.findByUserId(userId);
        TotalsPerCategory totalsPerCategory = budgetBrosService.getTotalBudgetLeft(expenses, budget);
        return ResponseEntity.status(200).body(totalsPerCategory);
    }

}
