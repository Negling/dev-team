package ua.devteam.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.users.User;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

/**
 * This controller processing GET requests to managers cabinet page and its fragments.
 */
@Controller
@RequestMapping("/manage")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ManagersCabinetController {

    private TechnicalTasksService technicalTasksService;
    private ProjectsService projectsService;

    /**
     * Returns managers cabinet main page.
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String cabinet(Model model) {
        model.addAttribute("ranks", DeveloperRank.values());
        model.addAttribute("specializations", DeveloperSpecialization.values());

        return "management";
    }

    /**
     * Returns managers cabinet new technical tasks section.
     */
    @GetMapping("/fragments/manage_technical_tasks")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String manageTechnicalTasks(Model model) {
        model.addAttribute("technicalTasks", technicalTasksService.getAllUnassigned(true));

        return "/fragments/management/manage_technical_tasks";
    }

    /**
     * Returns managers cabinet form projects section.
     */
    @GetMapping("/fragments/manage_form_project")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String manageFormProject(Model model, Authentication auth) {
        model.addAttribute("pendingProjects",
                projectsService.getNewByManager(((User) auth.getPrincipal()).getId(), true));

        return "/fragments/management/manage_form_project";
    }

    /**
     * Returns managers cabinet running projects section.
     */
    @GetMapping("/fragments/manage_running_projects")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String manageRunningProjects(Model model, Authentication auth) {
        model.addAttribute("runningProjects",
                projectsService.getRunningByManager(((User) auth.getPrincipal()).getId(), false));

        return "/fragments/management/manage_running_projects";
    }

    /**
     * Returns managers cabinet complete projects section.
     */
    @GetMapping("/fragments/manage_complete_projects")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ULTRAMANAGER', 'ADMIN')")
    public String manageCompleteProjects(Model model, Authentication auth) {
        model.addAttribute("completeProjects",
                projectsService.getCompleteByManager(((User) auth.getPrincipal()).getId(), false));

        return "/fragments/management/manage_complete_projects";
    }
}
