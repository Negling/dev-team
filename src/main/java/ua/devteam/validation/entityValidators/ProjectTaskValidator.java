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

        // assume that to each devs request match locked devs
        projectTask.getRequestsForDevelopers().forEach(rfd -> {

            // count all hired devs on that project with requested rank and quantity
            long devsCounter = projectTask.getTasksDevelopmentData().stream().filter(taskDeveloper ->

                    taskDeveloper.getSpecialization().equals(rfd.getSpecialization()) &&
                            taskDeveloper.getRank().equals(rfd.getRank())
            ).count();

            // assume that that current devs request is accomplished
            if (devsCounter != rfd.getQuantity()) {

                errors.reject("validationErrors.devsTaskHireMismatch",
                        new Object[]{formatStringOverflow(projectTask.getName(), 15), rfd.getQuantity(),
                                rfd.getSpecialization(), rfd.getRank(), devsCounter}, null);
            }
        });

        // total quantity of requested devs
        long totalQuantity = projectTask.getRequestsForDevelopers()
                .stream().collect(Collectors.summingLong(RequestForDevelopers::getQuantity));

        // assume that hired devs count exactly match requested quantity
        if (totalQuantity != projectTask.getTasksDevelopmentData().size()) {

            errors.reject("validationErrors.devsTaskQuantityMismatch",
                    new Object[]{formatStringOverflow(projectTask.getName(), 15), totalQuantity,
                            projectTask.getTasksDevelopmentData().size()}, null);
        }
    }
}
