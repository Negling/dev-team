package ua.devteam.validation.formValidators;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.forms.CustomerRegistrationForm;
import ua.devteam.service.UsersService;

import java.util.ResourceBundle;

import static ua.devteam.validation.ValidationUtils.*;

@Component
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CustomerRegistrationFormValidator implements Validator {

    private UsersService usersService;
    private ResourceBundle validationProperties;

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerRegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerRegistrationForm form = (CustomerRegistrationForm) target;

        // Check if first name is empty
        rejectIfEmptyOrWhitespace(errors, "firstName", "validationErrors.emptyName",
                new Object[]{validationProperties.getString("firstName.minLength")});

        // Check first name max length
        checkStringMaxLength("firstName", form.getFirstName(), Integer.parseInt(validationProperties.getString("firstName.maxLength")),
                errors, "validationErrors.fieldMaxLength", new Object[]{validationProperties.getString("firstName.maxLength")});

        // Check if first name match pattern
        checkStringMatchPattern("firstName", form.getFirstName(), validationProperties.getString("firstName.regexp"), errors,
                "validationErrors.initialsPatternMismatch", null);

        // Check if last name is empty
        rejectIfEmptyOrWhitespace(errors, "lastName", "validationErrors.emptyLastName",
                new Object[]{validationProperties.getString("lastName.minLength")});

        // Check last name max length
        checkStringMaxLength("lastName", form.getLastName(), Integer.parseInt(validationProperties.getString("lastName.maxLength")),
                errors, "validationErrors.fieldMaxLength", new Object[]{validationProperties.getString("lastName.maxLength")});

        // Check if last name match pattern
        checkStringMatchPattern("lastName", form.getLastName(), validationProperties.getString("lastName.regexp"), errors,
                "validationErrors.initialsPatternMismatch", null);

        // Check if phone number is empty
        rejectIfEmptyOrWhitespace(errors, "phoneNumber", "validationErrors.emptyPhoneNumber",
                new Object[]{validationProperties.getString("phoneNumber.minLength")});

        // Check phone number max length
        checkStringMaxLength("phoneNumber", form.getPhoneNumber(), Integer.parseInt(validationProperties.getString("phoneNumber.maxLength")),
                errors, "validationErrors.fieldMaxLength", new Object[]{validationProperties.getString("phoneNumber.maxLength")});

        // Check if phone number match pattern
        checkStringMatchPattern("phoneNumber", form.getPhoneNumber(), validationProperties.getString("phoneNumber.regexp"),
                errors, "validationErrors.phonePatternMismatch", null);

        // Check if phone number is available
        if (!errors.hasFieldErrors("phoneNumber") && !usersService.isPhoneAvailable(form.getPhoneNumber())) {
            errors.rejectValue("phoneNumber", "validationErrors.phoneNumberIsNotAvailable");
        }

        // Check if phone confirmation match phone field
        checkIfEquals("confirmedPhoneNumber", form.getPhoneNumber(), form.getConfirmedPhoneNumber(), errors,
                "validationErrors.valuesMismatch", null);

        // Check if email is empty
        rejectIfEmptyOrWhitespace(errors, "email", "validationErrors.emptyEmail",
                new Object[]{validationProperties.getString("email.minLength")});

        // Check email max length
        checkStringMaxLength("email", form.getEmail(), Integer.parseInt(validationProperties.getString("email.maxLength")),
                errors, "validationErrors.fieldMaxLength", new Object[]{validationProperties.getString("email.maxLength")});

        // Check if email match pattern
        checkStringMatchPattern("email", form.getEmail(), validationProperties.getString("email.regexp"), errors,
                "validationErrors.emailPatternMismatch", null);

        // Check if email is available
        if (!errors.hasFieldErrors("email") && !usersService.isEmailAvailable(form.getEmail())) {
            errors.rejectValue("email", "validationErrors.emailIsNotAvailable");
        }

        // Check if email confirmation match email field
        checkIfEquals("confirmedEmail", form.getEmail(), form.getConfirmedEmail(), errors,
                "validationErrors.valuesMismatch", null);

        // Check if password is empty
        rejectIfEmptyOrWhitespace(errors, "password", "validationErrors.emptyPassword",
                new Object[]{validationProperties.getString("password.minLength")});

        // Check if password is too short
        checkStringMinLength("password", form.getPassword(), Integer.parseInt(validationProperties.getString("password.minLength")),
                errors, "validationErrors.emptyPassword", new Object[]{validationProperties.getString("password.minLength")});

        // Check password max length
        checkStringMaxLength("password", form.getPassword(), Integer.parseInt(validationProperties.getString("password.maxLength")),
                errors, "validationErrors.fieldMaxLength", new Object[]{validationProperties.getString("password.maxLength")});

        // Check if password confirmation match password field
        checkIfEquals("confirmedPassword", form.getPassword(), form.getConfirmedPassword(), errors,
                "validationErrors.valuesMismatch", null);

    }
}
