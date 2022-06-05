package net.bos.om.api.validation.validator;

import net.bos.om.api.domain.OrderType;
import net.bos.om.api.io.input.order.AddOrderInput;
import net.bos.om.api.validation.constraint.PriceConstraint;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PriceValidator implements ConstraintValidator<PriceConstraint, AddOrderInput> {

    @Override
    public void initialize(PriceConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AddOrderInput input, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.nonNull(input)) {
            if (StringUtils.equalsIgnoreCase(input.getOrderType(), OrderType.LIMIT.name())) {
                System.out.println(Objects.nonNull(input.getPrice()));
                if (!Objects.nonNull(input.getPrice()) || input.getPrice() <= 0) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("{order.limit.min.value}").addConstraintViolation();
                    return false;
                }
            } else if (StringUtils.equalsIgnoreCase(input.getOrderType(), OrderType.MARKET.name())) {
                if (Objects.nonNull(input.getPrice())) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("{order.market.no.price}").addConstraintViolation();
                    return false;
                }
            } else {
                constraintValidatorContext.buildConstraintViolationWithTemplate("{order.unknown.value}").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
