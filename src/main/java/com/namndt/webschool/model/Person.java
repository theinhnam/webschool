package com.namndt.webschool.model;

import com.namndt.webschool.Annotations.FieldsValueMatch;
import com.namndt.webschool.Annotations.PasswordWeak;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "person")
@FieldsValueMatch.list({
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match!"
        ),
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Password do not match!"
        )
})
public class Person extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    @Transient
    private String confirmEmail;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    @PasswordWeak
    private String pwd;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    @Transient
    private String confirmPwd;

    @OneToOne(cascade = CascadeType.PERSIST, targetEntity = Role.class)
    @JoinColumn(name = "role_id")
    private Role roleId;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    private Address addressId;

    @ManyToOne(targetEntity = EazyClass.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "class_id")
    private EazyClass eazyClass;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable( name = "person_courses",
            joinColumns = {
                @JoinColumn(name = "person_id", referencedColumnName = "personId")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "course_id", referencedColumnName = "courseId")
            })
    private Set<Course> courses = new HashSet<>();
}
