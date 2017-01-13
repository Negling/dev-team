package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import ua.devteam.service.TechnicalTasksService;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

import static ua.devteam.entity.enums.Role.Customer;

@Controller
public class BaseController {

    private TechnicalTasksService technicalTasksService;
    private ProjectsService projectsService;
    private DevelopersService developersService;

    @Autowired
    public BaseController(TechnicalTasksService technicalTasksService, ProjectsService projectsService,
                          DevelopersService developersService) {
        this.technicalTasksService = technicalTasksService;
        this.projectsService = projectsService;
        this.developersService = developersService;
    }

    @RequestMapping("/technicalTask")
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin', 'Customer')")
    public String showTechnicalTask(@RequestParam Long id, Model model, Authentication auth) {
        try {
            TechnicalTask technicalTask = technicalTasksService.getById(id, true);

            if (auth.getAuthorities().stream().findAny().get().equals(Customer)
                    && !Objects.equals(((User) auth.getPrincipal()).getId(), technicalTask.getCustomerId())) {

                throw new AccessDeniedException("Customer with this id not allowed to view this document!");
            }

            model.addAttribute("technicalTask", technicalTask);
            return "technicalTask";
        } catch (AccessDeniedException | EmptyResultDataAccessException ex) {

            model.addAttribute("requestURL", "/technicalTask?id=" + id);
            return "forward:/error-page-404";
        }
    }

    @RequestMapping("/project")
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin', 'Customer')")
    public String showProject(@RequestParam Long id, Model model, Authentication auth) {
        try {
            Project project = projectsService.getById(id, true);

            if (auth.getAuthorities().stream().findAny().get().equals(Customer)
                    && !Objects.equals(((User) auth.getPrincipal()).getId(), project.getCustomerId())) {

                throw new AccessDeniedException("Customer with this id not allowed to view this document!");
            }

            model.addAttribute("project", project);
            return "project";
        } catch (AccessDeniedException | EmptyResultDataAccessException ex) {

            model.addAttribute("requestURL", "/project?id=" + id);
            return "forward:/error-page-404";
        }
    }

    @RequestMapping("/developer")
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public String showDeveloper(@RequestParam Long id, Model model) {
        try {

            model.addAttribute("developer", developersService.getById(id));
            return "developer";
        } catch (EmptyResultDataAccessException ex) {

            model.addAttribute("requestURL", "/developer?id=" + id);
            return "forward:/error-page-404";
        }
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
