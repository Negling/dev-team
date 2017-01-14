package ua.devteam.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @RequestMapping
    @PreAuthorize("hasAuthority('Developer')")
    public String cabinet() {
        return "development";
    }


    @ModelAttribute
    @PreAuthorize("hasAuthority('Developer')")
    public void addAttributes(Model model, Authentication auth) {
        long currentDeveloperId = ((User) auth.getPrincipal()).getId();

        model.addAttribute("activeTaskData", taskDevelopmentDataService.getActive(currentDeveloperId));
        model.addAttribute("completeTasks", taskDevelopmentDataService.getComplete(currentDeveloperId));
    }
}
