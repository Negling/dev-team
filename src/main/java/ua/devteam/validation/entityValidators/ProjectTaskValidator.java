package ua.devteam.validation.entityValidators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.devteam.entity.tasks.ProjectTask;
import ua.devteam.entity.tasks.RequestForDevelopers;

import java.util.stream.Collectors;

import static ua.devteam.validation.ValidationUtils.formatStringOverflow;

@Component
public class ProjectTaskValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProjectTask.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectTask projectTask = (ProjectTask) target;

        projectTask.getRequestsForDevelopers().forEach(rfd -> {

            long devsCounter = projectTask.getTasksDevelopmentData().stream().filter(taskDeveloper ->

                    taskDeveloper.getSpecialization().equals(rfd.getSpecialization()) &&
                            taskDeveloper.getRank().equals(rfd.getRank())
            ).count();

            if (devsCounter != rfd.getQuantity()) {
                errors.reject("validationErrors.devsTaskHireMismatch",
                        new Object[]{formatStringOverflow(projectTask.getName()), rfd.getQuantity(),
                                rfd.getSpecialization(), rfd.getRank(), devsCounter}, null);
            }
        });

        long totalQuantity = projectTask.getRequestsForDevelopers()
                .stream().collect(Collectors.summingLong(RequestForDevelopers::getQuantity));

        if (totalQuantity != projectTask.getTasksDevelopmentData().size()) {
            errors.reject("validationErrors.devsTaskQuantityMismatch",
                    new Object[]{formatStringOverflow(projectTask.getName()), totalQuantity,
                            projectTask.getTasksDevelopmentData().size()}, null);
        }
    }
}
