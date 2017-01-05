package ua.devteam.validation.entityValidators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.users.Check;

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

        rejectIfEmpty(errors, "servicesCost", "validationErrors.emptyServicesCost");

        rejectIfEmpty(errors, "taxes", "validationErrors.emptyTaxes");
    }
}
