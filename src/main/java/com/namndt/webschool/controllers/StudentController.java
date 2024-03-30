package com.namndt.webschool.controllers;

import com.namndt.webschool.Repositories.PersonRepository;
import com.namndt.webschool.model.Person;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/displayCourses")
    public String displayCourse(HttpSession session, Model model){
        Person person = (Person) session.getAttribute("loggedInPerson");
        model.addAttribute("person", person);
        return "courses_enrolled";
    }
}
