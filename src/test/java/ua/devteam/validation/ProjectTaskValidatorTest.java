package ua.devteam.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.tasks.ProjectTask;
import ua.devteam.entity.tasks.RequestForDevelopers;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.validation.entityValidators.ProjectTaskValidator;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static ua.devteam.entity.enums.DeveloperRank.JUNIOR;
import static ua.devteam.entity.enums.DeveloperRank.MIDDLE;
import static ua.devteam.entity.enums.DeveloperSpecialization.*;

@RunWith(JUnit4.class)
public class ProjectTaskValidatorTest {
    private Validator validator;
    private ProjectTask testData;
    private Errors errors;

    {
        validator = new ProjectTaskValidator();
        testData = new ProjectTask();
    }

    @Before
    public void setUp() throws Exception {
        testData.setRequestsForDevelopers(new ArrayList<RequestForDevelopers>() {{
            add(new RequestForDevelopers(null, BACKEND, JUNIOR, 1));
        }});
        testData.setTasksDevelopmentData(new ArrayList<TaskDevelopmentData>() {{
            add(new TaskDevelopmentData(null, null, BACKEND, JUNIOR));
        }});
        errors = new BeanPropertyBindingResult(testData, "testData");
    }

    @Test
    public void incorrectDevRankTest() {
        testData.getTasksDevelopmentData().get(0).setRank(MIDDLE);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void incorrectDevSpecializationTest() {
        testData.getTasksDevelopmentData().get(0).setSpecialization(DBA);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void incorrectQuantityTest() {
        testData.getRequestsForDevelopers().get(0).setQuantity(2);

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(2));
    }

    @Test
    public void devsHireOverkillTest() {
        testData.getTasksDevelopmentData().add(new TaskDevelopmentData(null, null, DESIGNER, JUNIOR));

        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(1));
    }

    @Test
    public void successTest() {
        validator.validate(testData, errors);

        assertThat(errors.getErrorCount(), is(0));
    }
}
