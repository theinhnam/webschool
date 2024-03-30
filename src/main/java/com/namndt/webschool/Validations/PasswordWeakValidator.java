package com.namndt.webschool.Validations;

import com.namndt.webschool.Annotations.PasswordWeak;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class PasswordWeakValidator implements ConstraintValidator<PasswordWeak, String> {

    List<String> listPwdWeak;

    @Override
    public void initialize(PasswordWeak constraintAnnotation) {
        listPwdWeak = Arrays.asList("123456", "password", "abcdef");
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (s != null && !listPwdWeak.contains(s));
    }
}
