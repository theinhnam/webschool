package com.namndt.webschool.services;

import com.namndt.webschool.Constants.WebSchoolConstants;
import com.namndt.webschool.Repositories.PersonRepository;
import com.namndt.webschool.Repositories.RolesRepository;
import com.namndt.webschool.model.Person;
import com.namndt.webschool.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Insert new person into DB
     * */
    public boolean registerUser(Person person){
        boolean isSaved = false;
        Role role = rolesRepository.getByRoleName(WebSchoolConstants.ROLE_STUDENT);
        person.setRoleId(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        Person saved = personRepository.save(person);
        if(null != saved && saved.getPersonId() > 0){
            isSaved = true;
        }
        return isSaved;
    }
}
