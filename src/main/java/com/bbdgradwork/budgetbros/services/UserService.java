package com.bbdgradwork.budgetbros.services;

import com.bbdgradwork.budgetbros.model.Role;
import com.bbdgradwork.budgetbros.model.UserLogin;
import com.bbdgradwork.budgetbros.repository.RoleRepository;
import com.bbdgradwork.budgetbros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserLogin findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserLogin findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public UserLogin saveUser(UserLogin user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
//        return userRepository.save(user);
    return userRepository.save(user);
    }

}