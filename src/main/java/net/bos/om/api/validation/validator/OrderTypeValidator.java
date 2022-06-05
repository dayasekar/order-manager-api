package net.bos.om.api.validation.validator;

import net.bos.om.api.domain.OrderType;
import net.bos.om.api.validation.constraint.OrderTypeConstraint;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class OrderTypeValidator implements ConstraintValidator<OrderTypeConstraint, String> {

    @Override
    public void initialize(OrderTypeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(OrderType.values()).anyMatch(c -> StringUtils.equals(c.name(), s));
    }
}
