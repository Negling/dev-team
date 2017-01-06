package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.users.Check;
import ua.devteam.entity.users.Developer;
import ua.devteam.entity.users.User;
import ua.devteam.service.DevelopersService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage")
public class ManagersCabinetController extends AbstractEntityProcessingController {

    private ProjectsService projectsService;
    private TechnicalTasksService technicalTasksService;
    private DevelopersService developersService;
    private Validator checkValidator;
    private Validator projectValidator;

    @Autowired
    public ManagersCabinetController(ResourceBundleMessageSource messageSource, ProjectsService projectsService,
                                     TechnicalTasksService technicalTasksService, DevelopersService developersService,
                                     Validator checkValidator, Validator projectValidator) {
        super(messageSource);
        this.projectsService = projectsService;
        this.technicalTasksService = technicalTasksService;
        this.developersService = developersService;
        this.checkValidator = checkValidator;
        this.projectValidator = projectValidator;
    }

    @RequestMapping
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public String managersParlor() {
        return "management";
    }

    @ResponseBody
    @RequestMapping("/getDevelopers")
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public List<Developer> getDevelopers(@RequestParam DeveloperSpecialization specialization,
                                         @RequestParam DeveloperRank rank,
                                         @RequestParam String lastName) {

        return developersService.getAvailableDevelopers(specialization, rank, lastName);
    }

    @ResponseBody
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public Developer bindDeveloper(@RequestBody Map<String, Long> params) {
        return developersService.bind(params.get("devId"), params.get("taskId"));
    }


    @ResponseBody
    @RequestMapping(value = "/unbind", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public ResponseEntity unbindDeveloper(@RequestBody Long developerId) throws Exception {
        developersService.unbind(developerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/declineTechnicalTask", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public ResponseEntity declineTechnicalTask(@RequestBody Map<String, String> params) throws Exception {
        technicalTasksService.decline(Long.parseLong(params.get("technicalTaskId")), params.get("managerCommentary"));
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/formAsProject", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public ResponseEntity formTechnicalTaskAsProject(@RequestBody Long technicalTaskId, Authentication auth) throws Exception {
        projectsService.createProject(technicalTaskId, ((User) auth.getPrincipal()).getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/decline", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public ResponseEntity declineProject(@RequestBody Long projectId) {

        projectsService.decline(projectId);
        return new ResponseEntity(HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public ResponseEntity<List<String>> formProject(@RequestBody @Valid Check check, BindingResult bindingResult,
                                                    Locale locale) {
        Project project;
        Errors projectErrors;

        if (bindingResult.hasErrors()) {
            return generateDefaultResponse(new LinkedList<>(), bindingResult, locale);
        }

        project = projectsService.getById(check.getProjectId());
        projectErrors = new BeanPropertyBindingResult(project, "project");

        projectValidator.validate(project, projectErrors);

        if (!projectErrors.hasErrors()) {
            projectsService.confirmProject(check);
        }

        return generateDefaultResponse(new LinkedList<>(), projectErrors, locale);
    }


    @ModelAttribute
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public void addAttributes(Model model, Authentication auth) {
        model.addAttribute("pendingProjects", projectsService.getNewByManager(((User) auth.getPrincipal()).getId()));
        model.addAttribute("technicalTasks", technicalTasksService.getAllUnassigned());
        model.addAttribute("specializations", DeveloperSpecialization.values());
        model.addAttribute("ranks", DeveloperRank.values());
    }

    @InitBinder("check")
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(checkValidator);
    }
}
