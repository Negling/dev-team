package ua.devteam.validation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class CompositeValidator implements Validator {
    private List<Validator> entityValidators;

    @Autowired
    public CompositeValidator(List<Validator> entityValidators) {
        if (entityValidators == null) {
            throw new IllegalArgumentException("Validators list can't be null!");
        }
        this.entityValidators = entityValidators;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        Validator result = entityValidators.stream().filter(validator ->
                validator.supports(clazz)).findAny().orElse(null);

        // if there any validator in entityValidators list that supports current class - returns true
        return result != null;
    }

    @Override
    public void validate(Object target, Errors errors) {
        // finds and invokes validator that supports target entity class
        entityValidators.stream().filter(validator ->
                validator.supports(target.getClass())).findAny().get().validate(target, errors);
    }

    public void addValidator(Validator validator) {
        entityValidators.add(validator);
    }

    public void removeValidator(Validator validator) {
        entityValidators.remove(validator);
    }
}
