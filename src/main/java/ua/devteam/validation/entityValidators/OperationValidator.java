package ua.devteam.validation.entityValidators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.entity.tasks.RequestForDevelopers;

import java.util.ResourceBundle;

import static ua.devteam.validation.ValidationUtils.*;

@Component
public class OperationValidator implements Validator {

    private Validator requestForDevelopersValidator;
    private ResourceBundle validationProperties;

    @Autowired
    public OperationValidator(Validator requestForDevelopersValidator, ResourceBundle validationProperties) {
        if (requestForDevelopersValidator == null) {
            throw new IllegalArgumentException("RequestForDevelopers validator is necessary, and can't be null!");
        }
        if (!requestForDevelopersValidator.supports(RequestForDevelopers.class)) {
            throw new IllegalArgumentException("RequestForDevelopers validator must support RequestForDevelopers.class!");
        }
        this.requestForDevelopersValidator = requestForDevelopersValidator;
        this.validationProperties = validationProperties;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Operation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Operation operation = (Operation) target;

        // Check if name is empty
        rejectIfEmptyOrWhitespace(errors, "name", "validationErrors.emptyName",
                new Object[]{validationProperties.getString("name.minLength")});

        // Check name min length
        checkStringMinLength(operation.getName(), Integer.parseInt(validationProperties.getString("name.minLength")),
                errors, "validationErrors.fieldInsufficientLength",
                new Object[]{operation.getName(), validationProperties.getString("name.minLength")});

        // Check name max length
        checkStringMaxLength(operation.getName(), Integer.parseInt(validationProperties.getString("name.maxLength")),
                errors, "validationErrors.fieldLengthOverflow", new Object[]{formatStringOverflow(operation.getName(), 15),
                        validationProperties.getString("name.maxLength")});

        // Check if description is empty
        rejectIfEmptyOrWhitespace(errors, "description", "validationErrors.emptyDescription",
                new Object[]{validationProperties.getString("description.minLength")});

        // Check description max length
        checkStringMinLength(operation.getDescription(), Integer.parseInt(validationProperties.getString("description.minLength")),
                errors, "validationErrors.fieldInsufficientLength",
                new Object[]{operation.getDescription(), validationProperties.getString("description.minLength")});

        // Check description max length
        checkStringMaxLength(operation.getDescription(), Integer.parseInt(validationProperties.getString("description.maxLength")),
                errors, "validationErrors.fieldLengthOverflow",
                new Object[]{formatStringOverflow(operation.getDescription(), 15), validationProperties.getString("description.maxLength")});

        // Check if rfd is empty
        if (operation.getRequestsForDevelopers() == null || operation.getRequestsForDevelopers().size() == 0) {
            errors.reject("validationErrors.emptyRequestsForDevelopers");
        } else {
            // Validate nested requests for developers
            for (int i = 0; i < operation.getRequestsForDevelopers().size(); i++) {
                errors.pushNestedPath("requestsForDevelopers[" + i + "]");
                invokeValidator(requestForDevelopersValidator, operation.getRequestsForDevelopers().get(i), errors);
                errors.popNestedPath();
            }
        }
    }
}
