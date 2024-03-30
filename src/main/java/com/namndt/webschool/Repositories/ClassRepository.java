package com.namndt.webschool.Repositories;

import com.namndt.webschool.model.EazyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<EazyClass, Integer> {
}
