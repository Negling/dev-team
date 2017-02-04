package ua.devteam.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.forms.CustomerRegistrationForm;
import ua.devteam.service.UsersService;
import ua.devteam.validation.formValidators.CustomerRegistrationFormValidator;

import java.util.ResourceBundle;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class CustomerRegistrationFormValidatorTest {
    private UsersService usersServiceMock = mock(UsersService.class);
    private Validator validator = new CustomerRegistrationFormValidator(usersServiceMock,
            ResourceBundle.getBundle("properties/validation"));
    private CustomerRegistrationForm testData = new CustomerRegistrationForm();
    private Errors errors;

    @Before
    public void setUp() throws Exception {
        testData.setFirstName("Ivan");
        testData.setLastName("Reon");
        testData.setEmail("test@test.test");
        testData.setConfirmedEmail(testData.getEmail());
        testData.setPhoneNumber("+380967311976");
        testData.setConfirmedPhoneNumber(testData.getPhoneNumber());
        testData.setPassword("12345678");
        testData.setConfirmedPassword(testData.getPassword());

        when(usersServiceMock.isEmailAvailable(testData.getEmail())).thenReturn(true);
        when(usersServiceMock.isPhoneAvailable(testData.getPhoneNumber())).thenReturn(true);

        errors = new BeanPropertyBindingResult(testData, "testData");
    }

    @Test
    public void nullFirstNameTest() {
        testData.setFirstName(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void emptyFistNameTest() {
        testData.setFirstName("");

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void firstNameOverflowTest() {
        // 21 character against 20 max allowed
        testData.setFirstName("123456789012345678901");

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void firstNamePatternTest() {
        testData.setFirstName("Karl$");

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void nullLastNameTest() {
        testData.setLastName(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void emptyLastNameTest() {
        testData.setLastName("");

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void lastNameOverflowTest() {
        // 21 character against 20 max allowed
        testData.setLastName("123456789012345678901");

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void lastNamePatternTest() {
        testData.setLastName("K@rlos");

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void nullPhoneNumberTest() {
        testData.setPhoneNumber(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void emptyPhoneNumberTest() {
        testData.setPhoneNumber("");

        validator.validate(testData, errors);

        // 2 cuz of confirmed phone number mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void phoneNumberMinLengthTest() {
        // min length is 13
        testData.setPhoneNumber("+8909");

        validator.validate(testData, errors);

        // 2 cuz of confirmed phone number mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void phoneNumberMaxLengthTest() {
        // max length is 13
        testData.setPhoneNumber("+890989098909890989098909");

        validator.validate(testData, errors);

        // 2 cuz of confirmed phone number mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void phoneNumberPatternTest() {
        testData.setPhoneNumber("&hi");

        validator.validate(testData, errors);

        // 2 cuz of confirmed phone number mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void nullPhoneConfirmationTest() {
        testData.setConfirmedPhoneNumber(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void emptyPhoneConfirmationTest() {
        testData.setConfirmedPhoneNumber("");

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void confirmedNumberMatchTest() {
        testData.setConfirmedPhoneNumber(testData.getPhoneNumber().replace('3', '4'));

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void nullEmailTest() {
        testData.setEmail(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void emptyEmailTest() {
        testData.setEmail("");

        validator.validate(testData, errors);

        // 2 cuz of confirmed email mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void emailMinLengthTest() {
        testData.setEmail("a@a");

        validator.validate(testData, errors);

        //2 coz of patter mismatch too
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void emailMaxLengthTest() {
        testData.setEmail("supertopepicololololololoemail@aautoemail.tutueu.com");

        validator.validate(testData, errors);

        // 2 cuz of confirmed email mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void emailPatternOkTest() {
        testData.setEmail("email@aautoemail.tutueu.com");

        validator.validate(testData, errors);

        // 2 cuz of confirmed email mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void emailPatternNotOkTest() {
        testData.setEmail("@aautoemail.tutueu.com");

        validator.validate(testData, errors);

        // 2 cuz of confirmed email mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void nullEmailConfirmationTest() {
        testData.setConfirmedEmail(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void emptyEmailConfirmationTest() {
        testData.setConfirmedEmail("");

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void notMatchEmailConfirmationTest() {
        testData.setConfirmedEmail(testData.getEmail().substring(1));

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void nullPasswordTest() {
        testData.setPassword(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void emptyPasswordTest() {
        testData.setPassword("");

        validator.validate(testData, errors);

        // 2 cuz of confirmed password mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void passwordMinLengthTest() {
        // min length is 8
        testData.setPassword("012");

        validator.validate(testData, errors);

        // 2 cuz of confirmed password mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void passwordMaxLengthTest() {
        // min length is 18
        testData.setPassword("12345678901234567890");

        validator.validate(testData, errors);

        // 2 cuz of confirmed password mismatch
        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void nullPasswordConfirmationTest() {
        testData.setConfirmedPassword(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void emptyPasswordConfirmationTest() {
        testData.setConfirmedPassword("");

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void passwordConfirmationMismatchTest() {
        testData.setConfirmedPassword(testData.getPassword().substring(1));

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void successTest() {
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(0));
    }
}
