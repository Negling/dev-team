package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.projects.Check;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.entity.users.Developer;
import ua.devteam.entity.users.User;
import ua.devteam.service.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This controller process REST requests on managers cabinet page.
 */
@RestController
@RequestMapping("/manage")
public class ManagersCabinetRestController extends AbstractEntityProcessingController {

    private TechnicalTasksService technicalTasksService;
    private ProjectsService projectsService;
    private ChecksService checksService;
    private DevelopersService developersService;
    private TaskDevelopmentDataService taskDevelopmentDataService;
    private Validator projectValidator;

    @Autowired
    public ManagersCabinetRestController(MessageSource messageSource, TechnicalTasksService technicalTasksService,
                                         ProjectsService projectsService, ChecksService checksService,
                                         TaskDevelopmentDataService taskDevelopmentDataService, DevelopersService developersService,
                                         Validator projectValidator) {
        super(messageSource);
        this.technicalTasksService = technicalTasksService;
        this.projectsService = projectsService;
        this.checksService = checksService;
        this.developersService = developersService;
        this.taskDevelopmentDataService = taskDevelopmentDataService;
        this.projectValidator = projectValidator;
    }

    /**
     * Returns list of developers, or empty list if no suitable developers were found.
     */
    @GetMapping("/getDevelopers")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public List<Developer> getDevelopers(@RequestParam DeveloperSpecialization specialization,
                                         @RequestParam DeveloperRank rank,
                                         @RequestParam String lastName) {
        return developersService.getAvailableDevelopers(specialization, rank, lastName);
    }

    /**
     * Binds specified developer to specified task.
     */
    @PostMapping("/bind")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public TaskDevelopmentData bindDeveloper(@RequestBody Map<String, Long> params) {
        return taskDevelopmentDataService.bindDeveloper(params.get("devId"), params.get("taskId"));
    }


    /**
     * Unbinds specified developer to specified task.
     */
    @DeleteMapping("/unbind")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public ResponseEntity unbindDeveloper(@RequestBody Long developerId) throws Exception {
        taskDevelopmentDataService.unbindDeveloper(developerId);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Declines technical task.
     */
    @PutMapping("/declineTechnicalTask")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public ResponseEntity declineTechnicalTask(@RequestBody Map<String, String> params) throws Exception {
        technicalTasksService.decline(Long.parseLong(params.get("technicalTaskId")), params.get("managerCommentary"));

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Accepts technical task anf creates project based on accepted technical task.
     */
    @PostMapping("/formAsProject")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public ResponseEntity formTechnicalTaskAsProject(@RequestBody Long technicalTaskId, Authentication auth) throws Exception {
        technicalTasksService.accept(technicalTaskId, ((User) auth.getPrincipal()).getId());

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Declines project.
     */
    @PutMapping("/declineProject")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public ResponseEntity declineProject(@RequestBody Map<String, String> params) {
        projectsService.decline(Long.parseLong(params.get("projectId")), params.get("managerCommentary"));

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Creates and binds check to projects in case of check and project both valid formed.
     */
    @PutMapping("/accept")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public ResponseEntity<List<String>> formProject(@Valid @RequestBody Check check, BindingResult bindingResult,
                                                    Locale locale) {
        Project project;
        Errors projectErrors;

        if (bindingResult.hasErrors()) {
            return generateResponse(new LinkedList<>(), bindingResult, locale);
        }

        project = projectsService.getById(check.getProjectId(), true);
        projectErrors = new BeanPropertyBindingResult(project, "project");

        projectValidator.validate(project, projectErrors);

        if (!projectErrors.hasErrors()) {
            check.setCustomerId(project.getCustomerId());
            checksService.registerCheck(check);
        }

        return generateResponse(new LinkedList<>(), projectErrors, locale);
    }
}
