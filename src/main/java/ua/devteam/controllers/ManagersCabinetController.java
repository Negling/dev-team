package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.users.User;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

@Controller
@RequestMapping("/manage")
public class ManagersCabinetController {

    private TechnicalTasksService technicalTasksService;
    private ProjectsService projectsService;

    @Autowired
    public ManagersCabinetController(TechnicalTasksService technicalTasksService, ProjectsService projectsService) {
        this.technicalTasksService = technicalTasksService;
        this.projectsService = projectsService;
    }

    @RequestMapping
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public String cabinet() {
        return "management";
    }

    @RequestMapping("/fragments/manage_technical_tasks")
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public String manageTechnicalTasks(Model model) {
        model.addAttribute("technicalTasks", technicalTasksService.getAllUnassigned(true));

        return "/fragments/management/manage_technical_tasks";
    }

    @RequestMapping("/fragments/manage_form_project")
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public String manageFormProject(Model model, Authentication auth) {
        model.addAttribute("pendingProjects",
                projectsService.getNewByManager(((User) auth.getPrincipal()).getId(), true));

        return "/fragments/management/manage_form_project";
    }

    @RequestMapping("/fragments/manage_running_projects")
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public String manageRunningProjects(Model model, Authentication auth) {
        model.addAttribute("runningProjects",
                projectsService.getRunningByManager(((User) auth.getPrincipal()).getId(), true));

        return "/fragments/management/manage_running_projects";
    }

    @RequestMapping("/fragments/manage_complete_projects")
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public String manageCompleteProjects(Model model, Authentication auth) {
        model.addAttribute("completeProjects",
                projectsService.getCompleteByManager(((User) auth.getPrincipal()).getId(), false));

        return "/fragments/management/manage_complete_projects";
    }

    @ModelAttribute
    @PreAuthorize("hasAnyAuthority('Manager', 'Ultramanager', 'Admin')")
    public void addAttributes(Model model) {
        model.addAttribute("ranks", DeveloperRank.values());
        model.addAttribute("specializations", DeveloperSpecialization.values());
    }
}
