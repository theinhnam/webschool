package com.namndt.webschool.controllers;

import com.namndt.webschool.Repositories.PersonRepository;
import com.namndt.webschool.model.Person;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/dashboard")
    public String displayDashboard (Authentication auth, Model model, HttpSession session){
        model.addAttribute("roles", auth.getAuthorities().toString());
        Person person = personRepository.readByEmail(auth.getName());
        model.addAttribute("username", person.getName());
        session.setAttribute("loggedInPerson", person);
        return "dashboard.html";
    }
}
