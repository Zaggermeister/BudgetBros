package com.bbdgradwork.budgetbros.repository;

import com.bbdgradwork.budgetbros.model.UserLogin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserLogin, String> {
    UserLogin findByEmail(String email);
    UserLogin findByUserName(String userName);
}


//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//    User findByEmail(String email);
//    User findByUserName(String userName);
//}