package com.bbdgradwork.budgetbros.repository;

import com.bbdgradwork.budgetbros.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface Repository extends MongoRepository<User, String> {

}
