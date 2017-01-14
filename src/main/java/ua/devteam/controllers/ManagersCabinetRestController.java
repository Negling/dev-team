package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
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
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.entity.users.Check;
import ua.devteam.entity.users.Developer;
import ua.devteam.entity.users.User;
import ua.devteam.service.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class ManagersCabinetRestController extends AbstractEntityProcessingController{

    private TechnicalTasksService technicalTasksService;
    private ProjectsService projectsService;
    private ChecksService checksService;
    private DevelopersService developersService;
    private TaskDevelopmentDataService taskDevelopersService;
    private Validator projectValidator;

    @Autowired
    public ManagersCabinetRestController(ResourceBundleMessageSource messageSource, TechnicalTasksService technicalTasksService,
                                         ProjectsService projectsService, ChecksService checksService,
                                         TaskDevelopmentDataService taskDevelopersService, Validator projectValidator,
                                         DevelopersService developersService) {
        super(messageSource);
        this.technicalTasksService = technicalTasksService;
        this.projectsService = projectsService;
        this.checksService = checksService;
        this.developersService = developersService;
        this.taskDevelopersService = taskDevelopersService;
        this.projectValidator = projectValidator;
    }

    @RequestMapping("/getDevelopers")
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public List<Developer> getDevelopers(@RequestParam DeveloperSpecialization specialization,
                                         @RequestParam DeveloperRank rank,
                                         @RequestParam String lastName) {
        return developersService.getAvailableDevelopers(specialization, rank, lastName);
    }

    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public TaskDevelopmentData bindDeveloper(@RequestBody Map<String, Long> params) {
        return taskDevelopersService.bindDeveloper(params.get("devId"), params.get("taskId"));
    }


    @RequestMapping(value = "/unbind", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public ResponseEntity unbindDeveloper(@RequestBody Long developerId) throws Exception {

        taskDevelopersService.unbindDeveloper(developerId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/declineTechnicalTask", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public ResponseEntity declineTechnicalTask(@RequestBody Map<String, String> params) throws Exception {

        technicalTasksService.decline(Long.parseLong(params.get("technicalTaskId")), params.get("managerCommentary"));

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/formAsProject", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public ResponseEntity formTechnicalTaskAsProject(@RequestBody Long technicalTaskId, Authentication auth) throws Exception {
        technicalTasksService.accept(technicalTaskId, ((User) auth.getPrincipal()).getId());

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/declineProject", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public ResponseEntity declineProject(@RequestBody Map<String, String> params) {

        projectsService.decline(Long.parseLong(params.get("projectId")), params.get("managerCommentary"));

        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/accept", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public ResponseEntity<List<String>> formProject(@Valid @RequestBody Check check, BindingResult bindingResult,
                                                    Locale locale) {
        Project project;
        Errors projectErrors;

        if (bindingResult.hasErrors()) {
            return generateDefaultResponse(new LinkedList<>(), bindingResult, locale);
        }

        project = projectsService.getById(check.getProjectId(), true);
        projectErrors = new BeanPropertyBindingResult(project, "project");

        projectValidator.validate(project, projectErrors);

        if (!projectErrors.hasErrors()) {
            checksService.registerCheck(check);
        }

        return generateDefaultResponse(new LinkedList<>(), projectErrors, locale);
    }
}
