package ua.devteam.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.EntityUtils;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.validation.entityValidators.TechnicalTaskValidator;

import java.util.ResourceBundle;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TechnicalTaskValidatorTest {
    private Validator validator;
    private TechnicalTask testData;
    private Errors errors;

    {
        Validator operationValidator = mock(Validator.class);
        when(operationValidator.supports(Operation.class)).thenReturn(true);

        validator = new TechnicalTaskValidator(operationValidator, ResourceBundle.getBundle("properties/validation"));
    }

    @Before
    public void setUp() throws Exception {
        testData = EntityUtils.getValidTechnicalTask(1L);
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
    public void emptyOperationsTest() {
        testData.getOperations().clear();

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void successTest() {
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(0));
    }
}
