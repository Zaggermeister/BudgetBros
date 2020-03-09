package com.bbdgradwork.budgetbros.controller;

import com.bbdgradwork.budgetbros.model.Expense;
import com.bbdgradwork.budgetbros.model.UserLogin;
import com.bbdgradwork.budgetbros.model.UserExpense;
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
    public BudgetBrosRestController(BudgetBrosService budgetBrosService) {
        this.budgetBrosService = budgetBrosService;
    }

    @GetMapping("/mybudget")
    public String getBudget(@RequestParam(name="name", required=false, defaultValue="World") String name) {
        return "{cheese: R5000, bread: R10}";
    }

    @PostMapping("/expense")
    public ResponseEntity<String> addExpenses(@RequestBody Expense expense) {

        if(budgetBrosService.addExpense(expense)) {
            return ResponseEntity.status(201).body("Success");
        }
        return ResponseEntity.status(400).body("Failed");
    }

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody UserLogin user) {
        if (budgetBrosService.addUser(user)) {
            return ResponseEntity.status(201).body("Success");
        }
        return ResponseEntity.status(400).body("Failed");

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserLogin>> getAllUsers() {
        return ResponseEntity.status(200).body(userRepository.findAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<UserLogin>> getUser(@PathVariable("userId") String userId) {
        Optional<UserLogin> result = budgetBrosService.getUser(userId);
        if(result.isPresent())
            return ResponseEntity.status(200).body(result);
        else
            return ResponseEntity.status(404).body(result);

    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.status(200).body(expenseRepository.findAll());
    }

    @GetMapping("/userExpenses")
    public ResponseEntity<List<UserExpense>> getUserExpenses() {
        return ResponseEntity.status(200).body(userExpenseRepository.findAll());
    }

    @GetMapping("/userExpenses/{userId}")
    public ResponseEntity<List<UserExpense>> getUserExpenses(@PathVariable("userId") String userId) {
        return ResponseEntity.status(200).body(userExpenseRepository.findByCustomerId(userId));
    }

    @PostMapping("/userExpense")
    public ResponseEntity<String> addUserExpense(@RequestBody UserExpense userExpense) {
        userExpenseRepository.save(userExpense);
        return ResponseEntity.status(201).body("Success");
    }

    @DeleteMapping("/userExpense/{userExpenseId}")
    public ResponseEntity<List<UserExpense>> deleteUserExpense(@PathVariable("userExpenseId") String userExpenseId) {
        userExpenseRepository.deleteById(userExpenseId);
        return ResponseEntity.status(200).body(userExpenseRepository.findAll());
    }


}
