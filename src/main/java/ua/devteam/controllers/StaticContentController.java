package ua.devteam.controllers;

import lombok.AllArgsConstructor;
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

import java.util.ResourceBundle;

/**
 * This controller processing uncategorized requests that don't do changes to any entity state. GET requests mostly.
 */
@Controller
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class StaticContentController {

    private TechnicalTasksService technicalTasksService;
    private ProjectsService projectsService;
    private DevelopersService developersService;
    private TaskDevelopmentDataService taskDevelopmentDataService;
    private ResourceBundle pagesProperties;

    /**
     * Returns page that displays technical task with specified ID.
     */
    @GetMapping(value = "${technicalTask.page}", params = {"id"})
    @PostAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN') " +
            "or (hasAuthority('CUSTOMER') and #model[technicalTask].customerId == principal.id)")
    public String showTechnicalTask(@RequestParam Long id, Model model) {
        model.addAttribute(pagesProperties.getString("model.technicalTask"), technicalTasksService.getById(id, true));

        return pagesProperties.getString("technicalTask.page");
    }

    /**
     * Returns page that displays project with specified ID.
     */
    @GetMapping(value = "${project.page}", params = {"id"})
    @PostAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN') " +
            "or (hasAuthority('CUSTOMER') and #model[project].customerId == principal.id)")
    public String showProject(@RequestParam Long id, Model model) {
        model.addAttribute(pagesProperties.getString("model.project"), projectsService.getById(id, true));

        return pagesProperties.getString("project.page");
    }

    /**
     * Returns page that displays developer with specified ID.
     */
    @GetMapping(value = "${developer_.page}", params = {"id"})
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String showDeveloper(@RequestParam Long id, Model model) {
        model.addAttribute(pagesProperties.getString("model.developer"), developersService.getById(id));
        model.addAttribute(pagesProperties.getString("model.developersBackground"), taskDevelopmentDataService.getComplete(id));
        model.addAttribute(pagesProperties.getString("model.currentTask"), taskDevelopmentDataService.getActive(id));

        return pagesProperties.getString("developer_.page");
    }

    /**
     * Returns main project page.
     */
    @GetMapping("${main.page}")
    public String main() {
        return pagesProperties.getString("main.page");
    }

    /**
     * Returns login page with attribute based on request params.
     */
    @GetMapping("${login.page}")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "registered", required = false) String registered,
                        Model model) {
        if (error != null) {
            model.addAttribute(pagesProperties.getString("model.error"), "error");
        }
        if (logout != null) {
            model.addAttribute(pagesProperties.getString("model.logout"), "logout");
        }
        if (registered != null) {
            model.addAttribute(pagesProperties.getString("model.registered"), "registered");
        }

        return pagesProperties.getString("login.page");
    }

}
