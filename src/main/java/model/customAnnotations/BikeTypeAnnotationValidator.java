package model.customAnnotations;

import model.BikeType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class BikeTypeAnnotationValidator implements ConstraintValidator<BikeTypeAnnotation, BikeType> {

    private BikeType[] subset;

    @Override
    public void initialize(BikeTypeAnnotation constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(BikeType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
