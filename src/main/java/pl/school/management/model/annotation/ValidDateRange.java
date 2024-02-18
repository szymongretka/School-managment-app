package pl.school.management.model.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pl.school.management.util.DateRangeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {

    String message() default "Start date must be before exit date!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
