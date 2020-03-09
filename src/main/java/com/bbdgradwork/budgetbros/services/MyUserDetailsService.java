package com.bbdgradwork.budgetbros.services;

import com.bbdgradwork.budgetbros.model.Role;
import com.bbdgradwork.budgetbros.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserLogin user = userService.findUserByUserName(userName);
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(UserLogin user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                user.getActive(), true, true, true, authorities);
    }
}