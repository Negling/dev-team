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

    @GetMapping(value = "/technicalTask", params = {"id"})
    @PostAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN') " +
            "or (hasAuthority('CUSTOMER') and #model[technicalTask].customerId == principal.id)")
    public String showTechnicalTask(@RequestParam Long id, Model model) {
        model.addAttribute("technicalTask", technicalTasksService.getById(id, true));

        return "technicalTask";
    }

    @GetMapping(value = "/project", params = {"id"})
    @PostAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN') " +
            "or (hasAuthority('CUSTOMER') and #model[project].customerId == principal.id)")
    public String showProject(@RequestParam Long id, Model model) {
        model.addAttribute("project", projectsService.getById(id, true));

        return "project";
    }

    @GetMapping(value = "/developer", params = {"id"})
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String showDeveloper(@RequestParam Long id, Model model) {
        model.addAttribute("developer", developersService.getById(id));
        model.addAttribute("developersBackground", taskDevelopmentDataService.getComplete(id));
        model.addAttribute("currentTask", taskDevelopmentDataService.getActive(id));

        return "developer";
    }

    @GetMapping("/")
    public String main() {
        return "main";
    }

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
