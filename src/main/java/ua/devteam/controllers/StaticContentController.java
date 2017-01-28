package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.devteam.service.DevelopersService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TaskDevelopmentDataService;
import ua.devteam.service.TechnicalTasksService;

/**
 * This controller processing uncategorized requests that don't do changes to any entity state. GET requests mostly.
 */
@Controller
public class StaticContentController {

    private TechnicalTasksService technicalTasksService;
    private ProjectsService projectsService;
    private DevelopersService developersService;
    private TaskDevelopmentDataService taskDevelopmentDataService;

    @Autowired
    public StaticContentController(TechnicalTasksService technicalTasksService, ProjectsService projectsService,
                                   DevelopersService developersService, TaskDevelopmentDataService taskDevelopmentDataService) {
        this.technicalTasksService = technicalTasksService;
        this.projectsService = projectsService;
        this.developersService = developersService;
        this.taskDevelopmentDataService = taskDevelopmentDataService;
    }

    /**
     * Returns page that displays technical task with specified ID.
     */
    @GetMapping(value = "/technicalTask", params = {"id"})
    @PostAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN') " +
            "or (hasAuthority('CUSTOMER') and #model[technicalTask].customerId == principal.id)")
    public String showTechnicalTask(@RequestParam Long id, Model model) {
        model.addAttribute("technicalTask", technicalTasksService.getById(id, true));

        return "technicalTask";
    }

    /**
     * Returns page that displays project with specified ID.
     */
    @GetMapping(value = "/project", params = {"id"})
    @PostAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN') " +
            "or (hasAuthority('CUSTOMER') and #model[project].customerId == principal.id)")
    public String showProject(@RequestParam Long id, Model model) {
        model.addAttribute("project", projectsService.getById(id, true));

        return "project";
    }

    /**
     * Returns page that displays developer with specified ID.
     */
    @GetMapping(value = "/developer", params = {"id"})
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String showDeveloper(@RequestParam Long id, Model model) {
        model.addAttribute("developer", developersService.getById(id));
        model.addAttribute("developersBackground", taskDevelopmentDataService.getComplete(id));
        model.addAttribute("currentTask", taskDevelopmentDataService.getActive(id));

        return "developer";
    }

    /**
     * Returns main project page.
     */
    @GetMapping("/")
    public String main() {
        return "main";
    }

    /**
     * Returns login page with attribute based on request params.
     */
    @GetMapping("/login")
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
