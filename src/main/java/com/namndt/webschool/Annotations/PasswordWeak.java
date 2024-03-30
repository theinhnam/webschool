package com.namndt.webschool.Annotations;

import com.namndt.webschool.Validations.PasswordWeakValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PasswordWeakValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordWeak {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Please choose a strong password";
}
