package com.bbdgradwork.budgetbros.repository;

import com.bbdgradwork.budgetbros.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {

}
