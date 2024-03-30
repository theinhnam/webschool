package com.namndt.webschool.controllers;

import com.namndt.webschool.Constants.WebSchoolConstants;
import com.namndt.webschool.model.Contact;
import com.namndt.webschool.proxy.ContactProxy;
import com.namndt.webschool.services.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@Slf4j
public class ContactController{

    @Autowired
    ContactService contactService;

    @Autowired
    ContactProxy contactProxy;

    @GetMapping("/contact")
    public String index(Model model){
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }

    @PostMapping("/saveMsg")
    public String saveMsg(@Valid @ModelAttribute("contact") Contact contact, Errors errors){
        if(errors.hasErrors()){
            return "contact.html";
        }
        contactService.saveMessageDetail(contact);
        return "redirect:/contact";
    }

    @GetMapping("/displayMessages/page/{page}")
    public ModelAndView displayMessage(Model model, @PathVariable("page") int page, @RequestParam(value = "sortField", required = true) String sortField, @RequestParam(value = "sortDir", required = true) String sortDir){
        ModelAndView messageView = new ModelAndView("messages.html");
        Page<Contact> pageContact = contactService.findMsgWithStatus(WebSchoolConstants.OPEN, page, sortField, sortDir);
        model.addAttribute("currentPage", page);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("totalPages", pageContact.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        messageView.addObject("contactMsgs", pageContact.getContent());
        return messageView;
    }

    @GetMapping("/closeMsg")
    public String closeMessage (@RequestParam("id") int id) {
        contactService.updateMessage(id);
        return "redirect:/displayMessages";
    }

}
