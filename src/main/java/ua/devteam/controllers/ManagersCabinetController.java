package ua.devteam.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.users.User;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

import java.util.ResourceBundle;

/**
 * This controller processing GET requests to managers cabinet page and its fragments.
 */
@Controller
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ManagersCabinetController {

    private TechnicalTasksService technicalTasksService;
    private ProjectsService projectsService;
    private ResourceBundle pagesProperties;

    /**
     * Returns managers cabinet main page.
     */
    @GetMapping("${manager.page}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String cabinet(Model model) {
        model.addAttribute(pagesProperties.getString("model.ranks"), DeveloperRank.values());
        model.addAttribute(pagesProperties.getString("model.specializations"), DeveloperSpecialization.values());

        return pagesProperties.getString("manager.page");
    }

    /**
     * Returns managers cabinet new technical tasks section.
     */
    @GetMapping("${manager.technicalTasks}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String manageTechnicalTasks(Model model) {
        model.addAttribute(pagesProperties.getString("model.technicalTasks"), technicalTasksService.getAllUnassigned(true));

        return pagesProperties.getString("manager.technicalTasks");
    }

    /**
     * Returns managers cabinet form projects section.
     */
    @GetMapping("${manager.formProjects}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String manageFormProject(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.pendingProjects"),
                projectsService.getNewByManager(currentUser.getId(), true));

        return pagesProperties.getString("manager.formProjects");
    }

    /**
     * Returns managers cabinet running projects section.
     */
    @GetMapping("${manager.runningProjects}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String manageRunningProjects(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.runningProjects"),
                projectsService.getRunningByManager(currentUser.getId(), false));

        return pagesProperties.getString("manager.runningProjects");
    }

    /**
     * Returns managers cabinet complete projects section.
     */
    @GetMapping("${manager.completeProjects}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String manageCompleteProjects(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.completeProjects"),
                projectsService.getCompleteByManager(currentUser.getId(), false));

        return pagesProperties.getString("manager.completeProjects");
    }
}
