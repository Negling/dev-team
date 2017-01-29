package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.devteam.entity.users.User;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.*;

/**
 * This controller process REST requests on developers cabinet page.
 */
@RestController
@RequestMapping("/development")
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
    @PutMapping("/completeTask")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public ResponseEntity<List<String>> completeTask(@RequestBody Map<String, String> params, Locale locale,
                                                     Authentication auth) {
        int hoursSpent = Integer.parseInt(params.get("hoursSpent"));

        if (isAcceptableHoursSpentValue(hoursSpent)) {
            taskDevelopmentDataService.complete(Long.parseLong(params.get("id")), ((User) auth.getPrincipal()).getId(),
                    hoursSpent);

            return getDefaultSuccessResponse(new LinkedList<>(), locale);
        } else {
            return getDefaultErrorResponse(new LinkedList<>(), locale);
        }
    }

    /**
     * Returns true if value is in acceptable values diapason.
     */
    private boolean isAcceptableHoursSpentValue(int value) {
        int minValue = Integer.parseInt(validationProperties.getString("hoursSpent.minValue"));
        int maxValue = Integer.parseInt(validationProperties.getString("hoursSpent.maxValue"));

        return value > minValue && value < maxValue;
    }
}
