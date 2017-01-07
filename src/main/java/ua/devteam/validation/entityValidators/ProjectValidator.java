package ua.devteam.validation.entityValidators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.tasks.ProjectTask;

import static org.springframework.validation.ValidationUtils.invokeValidator;

@Component
public class ProjectValidator implements Validator {

    private Validator projectTaskValidator;

    @Autowired
    public ProjectValidator(Validator projectTaskValidator) {
        if (projectTaskValidator == null) {
            throw new IllegalArgumentException("Operation validator is necessary, and can't be null!");
        }
        if (!projectTaskValidator.supports(ProjectTask.class)) {
            throw new IllegalArgumentException("Operation validator must support Operation.class!");
        }
        this.projectTaskValidator = projectTaskValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Project.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Project project = (Project) target;

        for (int i = 0; i < project.getTasks().size(); i++) {
            errors.pushNestedPath("tasks[" + i + "]");
            invokeValidator(projectTaskValidator, project.getTasks().get(i), errors);
            errors.popNestedPath();
        }

    }
}
