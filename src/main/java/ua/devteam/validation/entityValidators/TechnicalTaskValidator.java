package ua.devteam.validation.entityValidators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.tasks.Operation;

import static ua.devteam.validation.ValidationUtils.*;

@Component
public class TechnicalTaskValidator implements Validator {

    private Validator operationValidator;

    @Autowired
    public TechnicalTaskValidator(Validator operationValidator) {
        if (operationValidator == null) {
            throw new IllegalArgumentException("Operation validator is necessary, and can't be null!");
        }
        if (!operationValidator.supports(Operation.class)) {
            throw new IllegalArgumentException("Operation validator must support Operation.class!");
        }
        this.operationValidator = operationValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TechnicalTask.class.equals(clazz);
    }

    @Override
    public void validate(Object validationObject, Errors errors) {
        TechnicalTask techTask = (TechnicalTask) validationObject;

        // Check if name is empty
        rejectIfEmptyOrWhitespace(errors, "name", "validationErrors.emptyName", new Object[]{10});

        // Check name min length
        checkStringMinLength(techTask.getName(), 10, errors, "validationErrors.fieldInsufficientLength",
                new Object[]{techTask.getName(), 10});

        // Check name max length
        checkStringMaxLength(techTask.getName(), 50, errors, "validationErrors.fieldLengthOverflow",
                new Object[]{formatStringOverflow(techTask.getName()), 50});

        // Check if description is empty
        rejectIfEmptyOrWhitespace(errors, "description", "validationErrors.emptyDescription", new Object[]{30});

        // Check description max length
        checkStringMinLength(techTask.getDescription(), 30, errors, "validationErrors.fieldInsufficientLength",
                new Object[]{techTask.getDescription(), 30});

        // Check description max length
        checkStringMaxLength(techTask.getDescription(), 5000, errors, "validationErrors.fieldLengthOverflow",
                new Object[]{formatStringOverflow(techTask.getDescription()), 5000});

        // Check if operations is empty
        if (techTask.getOperations() == null|| techTask.getOperations().size() == 0) {
            errors.reject("validationErrors.emptyOperations");
        } else {
            // Validate nested operations
            for (int i = 0; i < techTask.getOperations().size(); i++) {
                errors.pushNestedPath("operations[" + i + "]");
                invokeValidator(operationValidator, techTask.getOperations().get(i), errors);
                errors.popNestedPath();
            }
        }
    }
}
