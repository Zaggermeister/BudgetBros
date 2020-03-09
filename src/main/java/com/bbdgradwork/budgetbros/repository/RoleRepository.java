package com.bbdgradwork.budgetbros.repository;

import com.bbdgradwork.budgetbros.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//public class RoleRepository {
//}
//

@Repository
public interface RoleRepository extends MongoRepository<Role, Integer> {
    Role findByRole(String role);

}