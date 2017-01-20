package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.devteam.entity.users.User;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/development")
public class DevelopersCabinetRestController extends AbstractEntityProcessingController {

    private TaskDevelopmentDataService taskDevelopmentDataService;

    @Autowired
    public DevelopersCabinetRestController(MessageSource messageSource, TaskDevelopmentDataService taskDevelopmentDataService) {
        super(messageSource);
        this.taskDevelopmentDataService = taskDevelopmentDataService;
    }

    @PreAuthorize("hasAuthority('Developer')")
    @RequestMapping(value = "/completeTask", method = RequestMethod.PUT)
    public ResponseEntity<List<String>> completeTask(@RequestBody Map<String, String> params, Locale locale,
                                                     Authentication auth) {
        int hoursSpent = Integer.parseInt(params.get("hoursSpent"));

        if (hoursSpent < 1 || hoursSpent > 999) {
            return getDefaultErrorResponse(new LinkedList<>(), locale);
        } else {
            taskDevelopmentDataService.complete(Long.parseLong(params.get("id")), ((User) auth.getPrincipal()).getId(),
                    hoursSpent);

            return getDefaultSuccessResponse(new LinkedList<>(), locale);
        }

    }
}
