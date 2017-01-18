package ua.devteam.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.validation.Validator;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.validation.entityValidators.ProjectValidator;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class CompositeValidatorTest {
    private CompositeValidator validator = new CompositeValidator(new ArrayList<>());

    @Test
    public void supportsTest() {
        Project testData = new Project();
        Validator testValidator = mock(ProjectValidator.class);

        when(testValidator.supports(Project.class)).thenReturn(true);
        validator.addValidator(testValidator);

        // assert that composite validator now supports Projects class
        assertTrue(validator.supports(Project.class));
        assertFalse(validator.supports(TechnicalTask.class));

        validator.validate(testData, null);

        // assert that Project validator has invoked on project validate call
        verify(testValidator, times(1)).validate(testData, null);
    }
}
