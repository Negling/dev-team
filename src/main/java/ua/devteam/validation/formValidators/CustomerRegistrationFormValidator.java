package ua.devteam.validation.formValidators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.forms.CustomerRegistrationForm;
import ua.devteam.service.UsersService;

import static ua.devteam.validation.ValidationUtils.*;

@Component
public class CustomerRegistrationFormValidator implements Validator {

    private UsersService usersService;

    @Autowired
    public CustomerRegistrationFormValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerRegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerRegistrationForm form = (CustomerRegistrationForm) target;

        // Check if first name is empty
        rejectIfEmptyOrWhitespace(errors, "firstName", "validationErrors.emptyName", new Object[]{1});

        // Check first name max length
        checkStringMaxLength("firstName", form.getFirstName(), 20, errors, "validationErrors.fieldMaxLength",
                new Object[]{20});

        // Check if first name match pattern
        checkStringMatchPattern("firstName", form.getFirstName(), "([А-Яа-яA-Za-z]+)", errors,
                "validationErrors.initialsPatternMismatch", null);

        // Check if last name is empty
        rejectIfEmptyOrWhitespace(errors, "lastName", "validationErrors.emptyLastName", new Object[]{1});

        // Check last name max length
        checkStringMaxLength("lastName", form.getLastName(), 20, errors, "validationErrors.fieldMaxLength",
                new Object[]{20});

        // Check if last name match pattern
        checkStringMatchPattern("lastName", form.getLastName(), "([А-Яа-яA-Za-z]+)", errors,
                "validationErrors.initialsPatternMismatch", null);

        // Check if phone number is empty
        rejectIfEmptyOrWhitespace(errors, "phoneNumber", "validationErrors.emptyPhoneNumber", new Object[]{13});

        // Check phone number max length
        checkStringMaxLength("phoneNumber", form.getPhoneNumber(), 13, errors, "validationErrors.fieldMaxLength",
                new Object[]{13});

        // Check if phone number match pattern
        checkStringMatchPattern("phoneNumber", form.getPhoneNumber(), "(\\+[0-9]{12})",
                errors, "validationErrors.phonePatternMismatch", null);

        // Check if phone number is available
        if (!errors.hasFieldErrors("phoneNumber") && !usersService.isPhoneAvailable(form.getPhoneNumber())) {
            errors.rejectValue("phoneNumber", "validationErrors.phoneNumberIsNotAvailable");
        }

        // Check if phone confirmation match phone field
        checkIfEquals("confirmedPhoneNumber", form.getPhoneNumber(), form.getConfirmedPhoneNumber(), errors,
                "validationErrors.valuesMismatch", null);

        // Check if email is empty
        rejectIfEmptyOrWhitespace(errors, "email", "validationErrors.emptyEmail", new Object[]{5});

        // Check email max length
        checkStringMaxLength("email", form.getEmail(), 30, errors, "validationErrors.fieldMaxLength",
                new Object[]{30});

        // Check if email match pattern
        checkStringMatchPattern("email", form.getEmail(), "([A-Za-z]+@([A-Za-z]+\\.)+[a-z]{1,15})",
                errors, "validationErrors.emailPatternMismatch", null);

        // Check if email is available
        if (!errors.hasFieldErrors("email") && !usersService.isEmailAvailable(form.getEmail())) {
            errors.rejectValue("email", "validationErrors.emailIsNotAvailable");
        }

        // Check if email confirmation match email field
        checkIfEquals("confirmedEmail", form.getEmail(), form.getConfirmedEmail(), errors,
                "validationErrors.valuesMismatch", null);

        // Check if password is empty
        rejectIfEmptyOrWhitespace(errors, "password", "validationErrors.emptyPassword", new Object[]{8});

        // Check if password is too short
        checkStringMinLength("password", form.getPassword(), 8,  errors, "validationErrors.emptyPassword", new Object[]{8});

        // Check password max length
        checkStringMaxLength("password", form.getPassword(), 18, errors, "validationErrors.fieldMaxLength",
                new Object[]{18});

        // Check if password confirmation match password field
        checkIfEquals("confirmedPassword", form.getPassword(), form.getConfirmedPassword(), errors,
                "validationErrors.valuesMismatch", null);

    }
}
