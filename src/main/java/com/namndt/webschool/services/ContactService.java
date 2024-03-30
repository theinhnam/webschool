package com.namndt.webschool.services;

import com.namndt.webschool.Constants.WebSchoolConstants;
import com.namndt.webschool.Repositories.ContactRepository;
import com.namndt.webschool.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactService {

    @Autowired
    ContactRepository contactRepo;

    /**
     * Save contact into DB
     * @Param: Contact
     * @Return: true or false
     * **/
    public boolean saveMessageDetail(Contact contact){
        boolean isSave = false;
        contact.setStatus(WebSchoolConstants.OPEN);
        Contact saved = contactRepo.save(contact);
        if (null != saved && saved.getContactId() > 0) {
            isSave = true;
        }
        return isSave;
    }

    /**
     * SELECT ALL MESSAGE FROM DB BY STATUS MESSAGE
     * @Param: status
     * */
    public Page<Contact> findMsgWithStatus (String status, int page, String sortFields, String sortDir) {
        Pageable pageable = PageRequest.of(page-1, 5, sortDir.equals("asc") ? Sort.by(sortFields).ascending() : Sort.by(sortFields).descending());
        return contactRepo.findByStatus(status, pageable);
    }

    /**
     * Update open message status to close
     * */
    public boolean updateMessage(int id){
        boolean isUpdate = false;
        Optional<Contact> contactUpd = contactRepo.findById(id);
        if (contactUpd.isPresent()) {
            contactUpd.get().setStatus(WebSchoolConstants.CLOSE);
        }
        Contact updated = contactRepo.save(contactUpd.get());
        if (updated.getUpdatedBy() != null && null != updated) {
            isUpdate = true;
        }
        return isUpdate;
    }

}
