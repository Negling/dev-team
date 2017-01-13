package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.users.User;
import ua.devteam.service.ChecksService;
import ua.devteam.service.CustomersService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/cabinet")
public class CustomersCabinetController extends AbstractEntityProcessingController {

    private CustomersService customersService;
    private ChecksService checksService;
    private ProjectsService projectsService;
    private TechnicalTasksService technicalTasksService;

    @Autowired
    public CustomersCabinetController(ResourceBundleMessageSource messageSource, CustomersService customersService,
                                      ChecksService checksService, ProjectsService projectsService, TechnicalTasksService technicalTasksService) {
        super(messageSource);
        this.customersService = customersService;
        this.checksService = checksService;
        this.projectsService = projectsService;
        this.technicalTasksService = technicalTasksService;
    }

    @RequestMapping
    @PreAuthorize("hasAuthority('Customer')")
    public String cabinet() {
        return "customers-cabinet";
    }

    @ResponseBody
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

    @ResponseBody
    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping(value = "/confirmCheck", method = RequestMethod.POST)
    public ResponseEntity confirmCheck(@RequestBody Long projectId) {
        checksService.accept(projectId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping(value = "/declineCheck", method = RequestMethod.POST)
    public ResponseEntity declineCheck(@RequestBody Long projectId) {
        checksService.decline(projectId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @ModelAttribute
    @PreAuthorize("hasAuthority('Customer')")
    public void addAttributes(Model model, Authentication auth) {
        long currentCustomerId = ((User) auth.getPrincipal()).getId();

        model.addAttribute("customer", customersService.getById(currentCustomerId));
        model.addAttribute("runningProjects", projectsService.getRunningByCustomer(currentCustomerId, false));
        model.addAttribute("completeProjects", projectsService.getCompleteByCustomer(currentCustomerId, false));
        model.addAttribute("technicalTasks", technicalTasksService.getAllByCustomer(currentCustomerId, false));
        model.addAttribute("newChecks", checksService.getNewByCustomer(currentCustomerId));
        model.addAttribute("completeChecks", checksService.getCompleteByCustomer(currentCustomerId));
        model.addAttribute("specializations", DeveloperSpecialization.values());
        model.addAttribute("ranks", DeveloperRank.values());
    }
}
