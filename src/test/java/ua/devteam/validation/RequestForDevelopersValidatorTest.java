package ua.devteam.validation;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.EntityUtils;
import ua.devteam.entity.tasks.RequestForDevelopers;
import ua.devteam.validation.entityValidators.RequestForDevelopersValidator;

import java.util.ResourceBundle;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class RequestForDevelopersValidatorTest {

    private Validator validator = new RequestForDevelopersValidator(ResourceBundle.getBundle("properties/validation"));
    private RequestForDevelopers testData;
    private Errors errors;

    @Before
    public void before() {
        testData = EntityUtils.getValidRequestForDevelopers();
        errors = new BeanPropertyBindingResult(testData, "testData");
    }

    @Test
    public void negativeQuantityTest() {
        testData.setQuantity(-1);
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void quantityOverflowTest() {
        testData.setQuantity(101);
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void normalQuantityTest() {
        testData.setQuantity(100);
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(0));
    }

    @Test
    public void nullQuantityTest() {
        testData.setQuantity(null);
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void nullSpecializationTest() {
        testData.setSpecialization(null);
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void nullRankTest() {
        testData.setRank(null);
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void successTest() {
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(0));
    }
}
