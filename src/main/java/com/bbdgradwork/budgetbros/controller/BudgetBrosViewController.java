package com.bbdgradwork.budgetbros.controller;

import com.bbdgradwork.budgetbros.model.User;
import com.bbdgradwork.budgetbros.services.BudgetBrosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
public class BudgetBrosViewController {

    private BudgetBrosService budgetBrosService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    //path params

    @RequestMapping(value={"/"}, method = RequestMethod.GET)
    public String main(/*@PathVariable("userId") String userId*/) {
        ModelAndView modelAndView = new ModelAndView();
//        String url = httpServletRequest.getRequestURL().toString();
//        Optional<User> result = budgetBrosService.getUser(userId);
        boolean userLoggedIn = true;//budgetBrosService.getUser(userId).get().getActive();

//        System.out.println(url);
        if (!userLoggedIn) {
            return  "redirect:/signin";
        }
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
}
