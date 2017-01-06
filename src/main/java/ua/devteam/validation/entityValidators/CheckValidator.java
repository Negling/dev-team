package ua.devteam.validation.entityValidators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.users.Check;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ua.devteam.validation.ValidationUtils.*;

@Component
public class CheckValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Check.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Check check = (Check) target;

        rejectIfEmpty(errors, "developersCost", "validationErrors.emptyDevelopersCost");

        if (check.getDevelopersCost() != null && check.getDevelopersCost().compareTo(new BigDecimal("0.00")) <= 0) {
            errors.reject("validationErrors.negativeOrNullDevelopersCost");
        }

        rejectIfEmpty(errors, "servicesCost", "validationErrors.emptyServicesCost");

        if (check.getServicesCost() != null && check.getServicesCost().compareTo(new BigDecimal("0.00")) <= 0) {
            errors.reject("validationErrors.negativeOrNullServicesCost");
        }

        rejectIfEmpty(errors, "taxes", "validationErrors.emptyTaxes");

        if (check.getDevelopersCost() != null && check.getServicesCost() != null && check.getTaxes() != null) {

            BigDecimal taxesValue = check.getDevelopersCost().add(check.getServicesCost()).multiply(new BigDecimal("0.2"))
                    .setScale(0, RoundingMode.HALF_UP);

            if (taxesValue.compareTo(check.getTaxes()) != 0) {
                errors.reject("validationErrors.incorrectTaxesValue");
            }
        }
    }
}
