package net.bos.om.api.validation.constraint;

import net.bos.om.api.validation.validator.OrderTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OrderTypeValidator.class)
public @interface OrderTypeConstraint {

    String message() default "{order.type.limitOrMarket.only}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
