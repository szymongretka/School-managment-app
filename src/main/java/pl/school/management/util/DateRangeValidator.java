package pl.school.management.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.school.management.model.dto.DateRange;
import pl.school.management.model.annotation.ValidDateRange;

import java.util.Objects;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, DateRange> {

    @Override
    public boolean isValid(DateRange dateRange, ConstraintValidatorContext context) {

        return Objects.nonNull(dateRange)
                && Objects.nonNull(dateRange.getEntryDate())
                && Objects.nonNull(dateRange.getExitDate())
                && dateRange.getEntryDate().isBefore(dateRange.getExitDate());
    }

}
