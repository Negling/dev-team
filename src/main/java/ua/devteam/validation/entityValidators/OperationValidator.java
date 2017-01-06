package ua.devteam.validation.entityValidators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.entity.tasks.RequestForDevelopers;

import static ua.devteam.validation.ValidationUtils.*;

@Component
public class OperationValidator implements Validator {

    private Validator requestForDevelopersValidator;

    @Autowired
    public OperationValidator(Validator requestForDevelopersValidator) {
        if (requestForDevelopersValidator == null) {
            throw new IllegalArgumentException("RequestForDevelopers validator is necessary, and can't be null!");
        }
        if (!requestForDevelopersValidator.supports(RequestForDevelopers.class)) {
            throw new IllegalArgumentException("RequestForDevelopers validator must support RequestForDevelopers.class!");
        }
        this.requestForDevelopersValidator = requestForDevelopersValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Operation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Operation operation = (Operation) target;

        // Check if name is empty
        rejectIfEmptyOrWhitespace(errors, "name", "validationErrors.emptyName", new Object[]{10});

        // Check name min length
        checkStringMinLength(operation.getName(), 10, errors, "validationErrors.fieldInsufficientLength",
                new Object[]{operation.getName(), 10});

        // Check name max length
        checkStringMaxLength(operation.getName(), 50, errors, "validationErrors.fieldLengthOverflow",
                new Object[]{formatStringOverflow(operation.getName()), 50});

        // Check if description is empty
        rejectIfEmptyOrWhitespace(errors, "description", "validationErrors.emptyDescription", new Object[]{30});

        // Check description max length
        checkStringMinLength(operation.getDescription(), 30, errors, "validationErrors.fieldInsufficientLength",
                new Object[]{operation.getDescription(), 30});

        // Check description max length
        checkStringMaxLength(operation.getDescription(), 5000, errors, "validationErrors.fieldLengthOverflow",
                new Object[]{formatStringOverflow(operation.getDescription()), 5000});

        // Check if rfd is empty
        if (operation.getRequestsForDevelopers() == null) {
            rejectIfEmpty(errors, "requestsForDevelopers", "validationErrors.emptyRequestsForDevelopers");
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
