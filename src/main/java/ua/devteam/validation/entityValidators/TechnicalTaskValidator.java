package ua.devteam.validation.entityValidators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.tasks.Operation;

import java.util.ResourceBundle;

import static ua.devteam.validation.ValidationUtils.*;

@Component
public class TechnicalTaskValidator implements Validator {

    private Validator operationValidator;
    private ResourceBundle validationProperties;

    @Autowired
    public TechnicalTaskValidator(Validator operationValidator, ResourceBundle validationProperties) {
        if (operationValidator == null) {
            throw new IllegalArgumentException("Operation validator is necessary, and can't be null!");
        }
        if (!operationValidator.supports(Operation.class)) {
            throw new IllegalArgumentException("Operation validator must support Operation.class!");
        }
        this.operationValidator = operationValidator;
        this.validationProperties = validationProperties;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TechnicalTask.class.equals(clazz);
    }

    @Override
    public void validate(Object validationObject, Errors errors) {
        TechnicalTask techTask = (TechnicalTask) validationObject;

        // Check if name is empty
        rejectIfEmptyOrWhitespace(errors, "name", "validationErrors.emptyName",
                new Object[]{validationProperties.getString("name.minLength")});

        // Check name min length
        checkStringMinLength(techTask.getName(), Integer.parseInt(validationProperties.getString("name.minLength")),
                errors, "validationErrors.fieldInsufficientLength",
                new Object[]{techTask.getName(), validationProperties.getString("name.minLength")});

        // Check name max length
        checkStringMaxLength(techTask.getName(), Integer.parseInt(validationProperties.getString("name.maxLength")),
                errors, "validationErrors.fieldLengthOverflow",
                new Object[]{formatStringOverflow(techTask.getName(), 15), validationProperties.getString("name.maxLength")});

        // Check if description is empty
        rejectIfEmptyOrWhitespace(errors, "description", "validationErrors.emptyDescription",
                new Object[]{validationProperties.getString("description.minLength")});

        // Check description max length
        checkStringMinLength(techTask.getDescription(), Integer.parseInt(validationProperties.getString("description.minLength")),
                errors, "validationErrors.fieldInsufficientLength",
                new Object[]{techTask.getDescription(), validationProperties.getString("description.minLength")});

        // Check description max length
        checkStringMaxLength(techTask.getDescription(), Integer.parseInt(validationProperties.getString("description.maxLength")),
                errors, "validationErrors.fieldLengthOverflow",
                new Object[]{formatStringOverflow(techTask.getDescription(), 15), validationProperties.getString("description.maxLength")});

        // Check if operations is empty
        if (techTask.getOperations() == null || techTask.getOperations().size() == 0) {
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
