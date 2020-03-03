package com.bbdgradwork.budgetbros.controller;

import com.bbdgradwork.budgetbros.model.Expense;
import com.bbdgradwork.budgetbros.model.User;
import com.bbdgradwork.budgetbros.repository.Repository;
import com.bbdgradwork.budgetbros.services.BudgetBrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1")
@RestController
public class BudgetBrosRestController {

    private final BudgetBrosService budgetBrosService;

    @Autowired
    Repository repository;

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

            return ResponseEntity.status(200).body("Success");
        }
        return ResponseEntity.status(500).body("Failed");
    }

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        if (budgetBrosService.addUser(user)) {
            return ResponseEntity.status(200).body("Success");
        } else {
            return ResponseEntity.status(500).body("Error");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(200).body(repository.findAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable("userId") String userId) {
        Optional<User> result = budgetBrosService.getUser(userId);
        if(result != null)
            return ResponseEntity.status(200).body(result);
        else
            return ResponseEntity.status(404).body(result);

    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
        repository.deleteById(userId);
        return ResponseEntity.status(200).body("Deleted user with userId: " + userId);
    }

}
