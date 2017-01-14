package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.users.User;
import ua.devteam.service.ChecksService;
import ua.devteam.service.TechnicalTasksService;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/cabinet")
public class CustomersCabinetRestController extends AbstractEntityProcessingController{

    private ChecksService checksService;
    private TechnicalTasksService technicalTasksService;

    @Autowired
    public CustomersCabinetRestController(ResourceBundleMessageSource messageSource, ChecksService checksService,
                                          TechnicalTasksService technicalTasksService) {
        super(messageSource);
        this.checksService = checksService;
        this.technicalTasksService = technicalTasksService;
    }

    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<List<String>> registerTechnicalTask(@RequestBody @Valid TechnicalTask technicalTask,
                                                              BindingResult bindingResult, Locale locale,
                                                              Authentication auth) {
        if (!bindingResult.hasErrors()) {
            technicalTask.setCustomerId(((User) auth.getPrincipal()).getId());
            technicalTasksService.registerTechnicalTask(technicalTask);
        }

        return generateDefaultResponse(new LinkedList<>(), bindingResult, locale);
    }

    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping(value = "/confirmCheck", method = RequestMethod.PUT)
    public ResponseEntity confirmCheck(@RequestBody Long projectId) {
        checksService.accept(projectId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping(value = "/declineCheck", method = RequestMethod.PUT)
    public ResponseEntity declineCheck(@RequestBody Long projectId) {
        checksService.decline(projectId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
