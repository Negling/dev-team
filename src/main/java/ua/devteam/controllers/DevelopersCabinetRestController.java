package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.devteam.entity.users.User;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This controller process REST requests on developers cabinet page.
 */
@RestController
public class DevelopersCabinetRestController extends AbstractEntityProcessingController {

    private TaskDevelopmentDataService taskDevelopmentDataService;
    private ResourceBundle validationProperties;

    @Autowired
    public DevelopersCabinetRestController(MessageSource messageSource, TaskDevelopmentDataService taskDevelopmentDataService,
                                           ResourceBundle validationProperties) {
        super(messageSource);
        this.taskDevelopmentDataService = taskDevelopmentDataService;
        this.validationProperties = validationProperties;
    }

    /**
     * Checks incoming params, and if they are valid - delegates task completion to service.
     */
    @PatchMapping("${developer.action.task}{taskId}")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public ResponseEntity<List<String>> completeTask(@PathVariable Long taskId, @RequestBody Integer hoursSpent, Locale locale,
                                                     @AuthenticationPrincipal User currentUser) {
        try {
            validateHoursSpentValue(hoursSpent);

            taskDevelopmentDataService.complete(taskId, currentUser.getId(), hoursSpent);

            return getDefaultSuccessResponse(new LinkedList<>(), locale);
        } catch (NumberFormatException ex) {
            return getDefaultErrorResponse(new LinkedList<>(), locale);
        }
    }

    /**
     * Throws {@link NumberFormatException ex} if value not in acceptable values diapason.
     */
    private void validateHoursSpentValue(int value) {
        int minValue = Integer.parseInt(validationProperties.getString("hoursSpent.minValue"));
        int maxValue = Integer.parseInt(validationProperties.getString("hoursSpent.maxValue"));

        if (value < minValue || value > maxValue) {
            throw new NumberFormatException();
        }
    }
}
