package com.namndt.webschool.Validations;

import com.namndt.webschool.Annotations.FieldsValueMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;

    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(o)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(o)
                .getPropertyValue(fieldMatch);
        if(null != fieldValue){
            return fieldValue.equals(fieldMatchValue);
        }else{
            return fieldValue == null;
        }
    }
}
