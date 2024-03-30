package com.namndt.webschool.Annotations;

import com.namndt.webschool.Validations.FieldsValueMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Fields value don't match";

    String field();

    String fieldMatch();

    @Target(value = {ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface list{
        FieldsValueMatch[] value();
    }
}
