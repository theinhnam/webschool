package com.namndt.webschool.controllers;

import com.namndt.webschool.Repositories.PersonRepository;
import com.namndt.webschool.model.Address;
import com.namndt.webschool.model.Person;
import com.namndt.webschool.model.Profile;
import com.namndt.webschool.services.ProfileService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/displayProfile")
    public String displayProfile(Model model, HttpSession session){
        Person person = (Person) session.getAttribute("loggedInPerson");
        Profile profile = new Profile();
        profile.setEmail(person.getEmail());
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        if(person.getAddressId() != null && person.getAddressId().getAddressId() > 0){
            profile.setAddress1(person.getAddressId().getAddress1());
            profile.setAddress2(person.getAddressId().getAddress2());
            profile.setState(person.getAddressId().getState());
            profile.setCity(person.getAddressId().getCity());
            profile.setZipCode(person.getAddressId().getZipCode());
        }
        model.addAttribute("profile", profile);
        return "profile.html";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors, HttpSession session){
        Person person = (Person) session.getAttribute("loggedInPerson");
        person.setEmail(profile.getEmail());
        person.setName(profile.getName());
        person.setMobileNumber(profile.getMobileNumber());
        if(person.getAddressId() == null || !(person.getAddressId().getAddressId() > 0)){
            person.setAddressId(new Address());
        }
        person.getAddressId().setAddress1(profile.getAddress1());
        person.getAddressId().setAddress2(profile.getAddress2());
        person.getAddressId().setCity(profile.getCity());
        person.getAddressId().setState(profile.getState());
        person.getAddressId().setZipCode(profile.getZipCode());
        personRepository.save(person);
        session.setAttribute("loggedInPerson", person);
        return "redirect:/dashboard";
    }
}
