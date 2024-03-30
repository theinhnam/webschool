package com.namndt.webschool.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Profile {

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Address1 must not be blank")
    @Size(min = 8, message = "Address1 must be at least 8 characters long")
    private String address1;

    @NotBlank(message = "Address1 must not be blank")
    @Size(min = 8, message = "Address2 must be at least 8 characters long")
    private String address2;

    @NotBlank(message = "City must not be blank")
    @Size(min = 5, message = "City must be at least 5 characters long")
    private String city;

    @NotBlank(message = "Address2 must not be blank")
    @Size(min = 5, message = "State must be at least 5 characters long")
    private String state;

    @NotNull(message = "Zip code must not be blank")
    private int zipCode;
}
