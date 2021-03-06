package ua.devteam.validation.entityValidators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.projects.Check;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ua.devteam.validation.ValidationUtils.rejectIfEmpty;


@Component
public class CheckValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Check.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Check check = (Check) target;

        // Check if developers cost is null
        rejectIfEmpty(errors, "developersCost", "validationErrors.emptyDevelopersCost");

        // If developers cost is not null - check if value is positive and not zero
        if (check.getDevelopersCost() != null && check.getDevelopersCost().compareTo(new BigDecimal("0.00")) <= 0) {
            errors.reject("validationErrors.negativeOrNullDevelopersCost");
        }

        // Check if services cost is null
        rejectIfEmpty(errors, "servicesCost", "validationErrors.emptyServicesCost");

        // If developers cost is not null - check if value is positive and not zero
        if (check.getServicesCost() != null && check.getServicesCost().compareTo(new BigDecimal("0.00")) <= 0) {
            errors.reject("validationErrors.negativeOrNullServicesCost");
        }

        // Check if taxes is null
        rejectIfEmpty(errors, "taxes", "validationErrors.emptyTaxes");

        // If all main fields is present - confirm that taxes vale is calculated right way - 20% from devsCost+servicesCost
        if (check.getDevelopersCost() != null && check.getServicesCost() != null && check.getTaxes() != null) {

            BigDecimal taxesValue = check.getDevelopersCost().add(check.getServicesCost())
                    .multiply(new BigDecimal("0.2"))
                    .setScale(0, RoundingMode.HALF_UP);

            if (taxesValue.compareTo(check.getTaxes()) != 0) {
                errors.reject("validationErrors.incorrectTaxesValue");
            }
        }
    }
}
