package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.users.User;
import ua.devteam.service.DevelopersService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TaskDevelopmentDataService;
import ua.devteam.service.TechnicalTasksService;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

import static ua.devteam.entity.enums.Role.Customer;

@Controller
public class BaseController {

    private TechnicalTasksService technicalTasksService;
    private ProjectsService projectsService;
    private DevelopersService developersService;
    private TaskDevelopmentDataService taskDevelopmentDataService;

    @Autowired
    public BaseController(TechnicalTasksService technicalTasksService, ProjectsService projectsService,
                          DevelopersService developersService, TaskDevelopmentDataService taskDevelopmentDataService) {
        this.technicalTasksService = technicalTasksService;
        this.projectsService = projectsService;
        this.developersService = developersService;
        this.taskDevelopmentDataService = taskDevelopmentDataService;
    }

    @RequestMapping(value = "/technicalTask", params = {"id"})
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin', 'Customer')")
    public String showTechnicalTask(@RequestParam Long id, Model model, Authentication auth) throws AccessDeniedException {
        TechnicalTask technicalTask = technicalTasksService.getById(id, true);
        User user = ((User) auth.getPrincipal());

        if (user.getRole().equals(Customer)
                && !Objects.equals(user.getId(), technicalTask.getCustomerId())) {

            throw new AccessDeniedException("Customer with this id not allowed to view this document!");
        }

        model.addAttribute("technicalTask", technicalTask);
        return "technicalTask";
    }

    @RequestMapping(value = "/project", params = {"id"})
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin', 'Customer')")
    public String showProject(@RequestParam Long id, Model model, Authentication auth) throws AccessDeniedException {
        Project project = projectsService.getById(id, true);
        User user = ((User) auth.getPrincipal());

        if (user.getRole().equals(Customer)
                && !Objects.equals(user.getId(), project.getCustomerId())) {

            throw new AccessDeniedException("Customer with this id not allowed to view this document!");
        }

        model.addAttribute("project", project);
        return "project";
    }

    @RequestMapping(value = "/developer", params = {"id"})
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public String showDeveloper(@RequestParam Long id, Model model) {
        model.addAttribute("developer", developersService.getById(id));
        model.addAttribute("developersBackground", taskDevelopmentDataService.getComplete(id));
        model.addAttribute("currentTask", taskDevelopmentDataService.getActive(id));

        return "developer";
    }

    @RequestMapping("/")
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "registered", required = false) String registered,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "error");
        }
        if (logout != null) {
            model.addAttribute("logout", "logout");
        }
        if (registered != null) {
            model.addAttribute("registered", "registered");
        }

        return "login";
    }

}
