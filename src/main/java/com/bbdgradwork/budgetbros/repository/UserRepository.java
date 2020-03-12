package com.bbdgradwork.budgetbros.repository;

import com.bbdgradwork.budgetbros.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {
    public User findByEmail(String email);
}
