package com.bbdgradwork.budgetbros.controller;


import com.bbdgradwork.budgetbros.model.UserLogin;
import com.bbdgradwork.budgetbros.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/", "/signin"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signin");
        return modelAndView;
    }


    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        UserLogin user = new UserLogin();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid UserLogin user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        UserLogin userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new UserLogin());
            modelAndView.setViewName("register");

        }
        return modelAndView;
    }

    @RequestMapping(value="/main", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserLogin user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.setViewName("main");
        return modelAndView;
    }


}