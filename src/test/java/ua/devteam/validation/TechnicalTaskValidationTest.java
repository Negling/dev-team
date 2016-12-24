package ua.devteam.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.validation.entityValidators.TechnicalTaskValidator;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class TechnicalTaskValidationTest {
    private Validator validator;
    private TechnicalTask testData;
    private Errors errors;

    {
        Validator operationValidator = mock(Validator.class);
        when(operationValidator.supports(Operation.class)).thenReturn(true);
        validator = new TechnicalTaskValidator(operationValidator);
        testData = new TechnicalTask();
    }

    @Before
    public void setUp() throws Exception {
        testData.setName("test_test_test");
        testData.setDescription("test_test_test_test_test_test_test_test_test");
        testData.setOperations(new ArrayList<>());
        errors = new BeanPropertyBindingResult(testData, "testData");
    }

    @Test
    public void nullNameTest() {
        testData.setName(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void nameMinLengthTest() {
        testData.setName("1");

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void nameMaxLengthTest() {
        testData.setName(testData.getDescription().concat(testData.getDescription()));

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void nullDescriptionTest() {
        testData.setDescription(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void descriptionMinLengthTest() {
        testData.setDescription(testData.getName());

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void nullOperationsTest() {
        testData.setOperations(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void successTest() {
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(0));
    }
}
