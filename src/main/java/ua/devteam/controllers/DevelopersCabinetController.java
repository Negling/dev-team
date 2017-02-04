package ua.devteam.controllers;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.devteam.entity.users.User;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.ResourceBundle;

/**
 * This controller processing GET requests to developers cabinet page and its fragments.
 */
@Controller
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DevelopersCabinetController {

    private TaskDevelopmentDataService taskDevelopmentDataService;
    private ResourceBundle pagesProperties;

    /**
     * Returns developers cabinet main page.
     */
    @GetMapping("${developer.page}")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String development() {
        return pagesProperties.getString("developer.page");
    }

    /**
     * Returns developers cabinet active task section.
     */
    @GetMapping("${developer.activeTask}")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String developmentActiveTask(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.activeTaskData"),
                taskDevelopmentDataService.getActive(currentUser.getId()));

        return pagesProperties.getString("developer.activeTask");
    }

    /**
     * Returns developers cabinet tasks history section.
     */
    @GetMapping("${developer.tasksHistory}")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String developmentTasksHistory(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.completeTasks"),
                taskDevelopmentDataService.getComplete(currentUser.getId()));

        return pagesProperties.getString("developer.tasksHistory");
    }
}
