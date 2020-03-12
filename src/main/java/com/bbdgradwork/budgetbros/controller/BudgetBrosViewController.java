package com.bbdgradwork.budgetbros.controller;

import com.bbdgradwork.budgetbros.model.User;
import com.bbdgradwork.budgetbros.services.BudgetBrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
public class BudgetBrosViewController {

    private BudgetBrosService budgetBrosService;

    @Autowired
    public BudgetBrosViewController(BudgetBrosService budgetBrosService) {
        this.budgetBrosService = budgetBrosService;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    //path params

    @RequestMapping(value={"/","/{userId}/signout"}, method = RequestMethod.GET)
    public String exit(@PathVariable(required = false, name="userId" ) String userId) {
        return "redirect:/signin";
    }

    @RequestMapping(value={"/","/{userId}"}, method = RequestMethod.GET)
    public String main(@PathVariable(required = false, name="userId" ) String userId) {
        ModelAndView modelAndView = new ModelAndView();
//        String url = httpServletRequest.getRequestURL().toString();

        System.out.println("userId " + " '" + userId+ "'");
        if (userId == null) {
            return "redirect:/signin";
        }
        Optional<User> result = budgetBrosService.getUser(userId);
        System.out.println(result);


        if (result.equals(Optional.empty())) {
            return "redirect:/signin";
        }
//        boolean userLoggedIn = budgetBrosService.getUser(userId).get().getActive();

        return  "main";
    }

    @GetMapping("/signin")
    public String login() {
        return "signin";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/signout")
    public String logout() {
        return "signin";
    }

}
