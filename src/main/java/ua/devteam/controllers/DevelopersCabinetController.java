package ua.devteam.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.devteam.entity.users.User;
import ua.devteam.service.TaskDevelopmentDataService;

@Controller
@RequestMapping("/development")
public class DevelopersCabinetController  {

    private TaskDevelopmentDataService taskDevelopmentDataService;

    @Autowired
    public DevelopersCabinetController(TaskDevelopmentDataService taskDevelopmentDataService) {
        this.taskDevelopmentDataService = taskDevelopmentDataService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String development() {
        return "development";
    }

    @GetMapping("/fragments/development_active_task")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String developmentActiveTask(Model model, Authentication auth) {
        model.addAttribute("activeTaskData",
                taskDevelopmentDataService.getActive(((User) auth.getPrincipal()).getId()));

        return "/fragments/development/development_active_task";
    }

    @GetMapping("/fragments/development_tasks_history")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String developmentTasksHistory(Model model, Authentication auth) {
        model.addAttribute("completeTasks",
                taskDevelopmentDataService.getComplete(((User) auth.getPrincipal()).getId()));

        return "/fragments/development/development_tasks_history";
    }
}
