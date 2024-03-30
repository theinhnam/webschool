package com.namndt.webschool.controllers;

import com.namndt.webschool.model.Person;
import com.namndt.webschool.services.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @GetMapping("/register")
    public String displayRegister(Model model){
        model.addAttribute("person", new Person());
        return "register.html";
    }

    @PostMapping("/registerUser")
    public String registerAccount(@Valid @ModelAttribute("person") Person person, Errors errors){
        if(errors.hasErrors()){
            return "register.html";
        }
        registerService.registerUser(person);
        return "redirect:/login?register=true";
    }
}
