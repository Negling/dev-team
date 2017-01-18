package ua.devteam.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.projects.Check;
import ua.devteam.validation.entityValidators.CheckValidator;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class CheckValidatorTest {
    private Validator validator;
    private Check testData;
    private Errors errors;

    {
        validator = new CheckValidator();
        testData = new Check();
    }

    @Before
    public void setUp() throws Exception {
        testData.setDevelopersCost(new BigDecimal("10.00"));
        testData.setServicesCost(new BigDecimal("10.00"));
        testData.setTaxes(new BigDecimal("4.00"));
        errors = new BeanPropertyBindingResult(testData, "testData");
    }

    @Test
    public void nullDevelopersCostTest() {
        testData.setDevelopersCost(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void incorrectDevelopersCostTest() {
        testData.setDevelopersCost(new BigDecimal("-1"));

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void nullServicesCostTest() {
        testData.setServicesCost(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void incorrectServicesCostTest() {
        testData.setServicesCost(new BigDecimal("-1"));

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void nullTaxesTest() {
        testData.setTaxes(null);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void incorrectTaxesTest() {
        testData.setTaxes(new BigDecimal("4.01"));

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void successTest() {
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(0));
    }
}
