package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.users.User;
import ua.devteam.service.ChecksService;
import ua.devteam.service.TechnicalTasksService;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * This controller process REST requests on customers cabinet page.
 */
@RestController
@RequestMapping("/cabinet")
public class CustomersCabinetRestController extends AbstractEntityProcessingController {

    private ChecksService checksService;
    private TechnicalTasksService technicalTasksService;

    @Autowired
    public CustomersCabinetRestController(MessageSource messageSource, ChecksService checksService,
                                          TechnicalTasksService technicalTasksService) {
        super(messageSource);
        this.checksService = checksService;
        this.technicalTasksService = technicalTasksService;
    }

    /**
     * If technicalTask passes validation - delegates its registration to service. Returns ResponseEntity with status
     * and messages about processing status.
     */
    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<List<String>> registerTechnicalTask(@RequestBody @Valid TechnicalTask technicalTask,
                                                              BindingResult bindingResult, Locale locale,
                                                              Authentication auth) {
        if (!bindingResult.hasErrors()) {
            technicalTask.setCustomerId(((User) auth.getPrincipal()).getId());
            technicalTasksService.registerTechnicalTask(technicalTask);
        }

        return generateResponse(new LinkedList<>(), bindingResult, locale);
    }

    /**
     * Confirms check and returns OK status.
     */
    @PutMapping("/confirmCheck")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity confirmCheck(@RequestBody Long projectId) {
        checksService.accept(projectId);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Declines check and returns OK status.
     */
    @PutMapping("/declineCheck")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity declineCheck(@RequestBody Long projectId) {
        checksService.decline(projectId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
