package com.namndt.webschool.Repositories;

import com.namndt.webschool.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    public Page<Contact> findByStatus(String status, Pageable pageable);

    public List<Contact> findByStatus(String status);
}
