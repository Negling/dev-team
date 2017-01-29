package ua.devteam.validation.entityValidators;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.tasks.RequestForDevelopers;

import java.util.ResourceBundle;

import static ua.devteam.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

@Component
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class RequestForDevelopersValidator implements Validator {

    private ResourceBundle validationProperties;

    @Override
    public boolean supports(Class<?> clazz) {
        return RequestForDevelopers.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestForDevelopers rfd = (RequestForDevelopers) target;

        // Check if specialization is empty
        rejectIfEmptyOrWhitespace(errors, "specialization", "validationErrors.emptySpecialization");

        // Check if rank is empty
        rejectIfEmptyOrWhitespace(errors, "rank", "validationErrors.emptyRank");

        // Check if quantity is empty
        rejectIfEmptyOrWhitespace(errors, "quantity", "validationErrors.emptyQuantity");

        // If quantity is null - skip subsequent checks
        if (rfd.getQuantity() == null) {
            return;
        }

        // Check if quantity is lower than 1
        if (rfd.getQuantity() < 1) {
            errors.reject("validationErrors.fieldInsufficientValue", new Object[]{rfd.getQuantity(),
                    validationProperties.getString("quantity.minValue")}, null);
        }

        // Check if quantity is greater than 100
        if (rfd.getQuantity() > 100) {
            errors.reject("validationErrors.fieldValueOverflow", new Object[]{rfd.getQuantity(),
                    validationProperties.getString("quantity.maxValue")}, null);
        }
    }
}
