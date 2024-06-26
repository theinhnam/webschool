package com.namndt.webschool.Repositories;

import com.namndt.webschool.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    public Person readByEmail(String email);
}
