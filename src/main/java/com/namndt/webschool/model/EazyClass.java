package com.namndt.webschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Table(name = "class")
@Entity
@Getter
@Setter
public class EazyClass extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classId;

    @Column(name = "name")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @OneToMany(mappedBy = "eazyClass", fetch = FetchType.LAZY, targetEntity = Person.class)
    private Set<Person> persons;
}
