package com.bbdgradwork.budgetbros.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        //do something like logging
        return "redirect:/signin";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
