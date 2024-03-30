package com.namndt.webschool.rest;

import com.namndt.webschool.Constants.WebSchoolConstants;
import com.namndt.webschool.Repositories.ContactRepository;
import com.namndt.webschool.model.Contact;
import com.namndt.webschool.model.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact")
public class ContactRestController {

    @Autowired
    ContactRepository contactRepository;

    @GetMapping("/getMessagesByStatus")
    public List<Contact> findMessageWithStatus(@RequestParam("status") String status){
        return contactRepository.findByStatus(status);
    }

    @GetMapping("/getAllMsgByStatus")
    public List<Contact> getMessageWithStatus(@RequestBody Contact contact){
        if (null!= contact && null != contact.getStatus()) {
            return contactRepository.findByStatus(contact.getStatus());
        }else{
            return  List.of();
        }
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@Valid @RequestBody Contact contact){
        contactRepository.save(contact);
        Response response = new Response();
        response.setStatusCode("201");
        response.setStatusMsg("Save message successfully!");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSave", "true")
                .body(response);
    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> contact){
        Optional<Contact> contactDelete = contactRepository.findById(contact.getBody().getContactId());
        if (contactDelete.get() == null || !(contactDelete.get().getContactId() > 0)) {
            Response response = new Response();
            response.setStatusCode(HttpStatus.NOT_FOUND.toString());
            response.setStatusMsg("Can not found contact");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        contactRepository.deleteById(contactDelete.get().getContactId());
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.toString());
        response.setStatusMsg("Delete successfully!");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/updateMsg")
    public ResponseEntity<Response> updateMsg(@RequestBody Contact contactReq){
        Response response = new Response();
        Optional<Contact> contactUpdate = contactRepository.findById(contactReq.getContactId());
        if(contactUpdate.isPresent()){
            contactUpdate.get().setStatus(WebSchoolConstants.CLOSE);
            contactRepository.save(contactUpdate.get());
        }else{
            response.setStatusCode(HttpStatus.NOT_FOUND.toString());
            response.setStatusMsg("Could not found contact message");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        response.setStatusCode(HttpStatus.OK.toString());
        response.setStatusMsg("Message successfully closed!");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
