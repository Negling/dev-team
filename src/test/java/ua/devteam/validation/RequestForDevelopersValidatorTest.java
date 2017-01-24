package ua.devteam.validation;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.tasks.RequestForDevelopers;
import ua.devteam.validation.entityValidators.RequestForDevelopersValidator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class RequestForDevelopersValidatorTest {

    private Validator validator = new RequestForDevelopersValidator();
    private RequestForDevelopers testData = new RequestForDevelopers();
    private Errors errors;

    @Before
    public void before() {
        testData.setSpecialization(DeveloperSpecialization.BACKEND);
        testData.setRank(DeveloperRank.JUNIOR);
        testData.setQuantity(1);
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
