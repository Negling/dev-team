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
import ua.devteam.service.ChecksService;
import ua.devteam.service.CustomersService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

@Controller
@RequestMapping("/cabinet")
public class CustomersCabinetController {

    private CustomersService customersService;
    private ChecksService checksService;
    private ProjectsService projectsService;
    private TechnicalTasksService technicalTasksService;

    @Autowired
    public CustomersCabinetController(CustomersService customersService, ChecksService checksService,
                                      ProjectsService projectsService, TechnicalTasksService technicalTasksService) {
        this.customersService = customersService;
        this.checksService = checksService;
        this.projectsService = projectsService;
        this.technicalTasksService = technicalTasksService;
    }

    @RequestMapping
    @PreAuthorize("hasAuthority('Customer')")
    public String cabinet() {
        return "customers-cabinet";
    }


    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping("/fragments/customer_new_checks")
    public String cabinetNewChecks(Model model, Authentication auth) {
        model.addAttribute("newChecks", checksService.getNewByCustomer(((User) auth.getPrincipal()).getId()));

        return "/fragments/customer/customer_new_checks";
    }

    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping("/fragments/customer_considered_checks")
    public String cabinetConsideredChecks(Model model, Authentication auth) {
        model.addAttribute("completeChecks", checksService.getCompleteByCustomer(((User) auth.getPrincipal()).getId()));

        return "/fragments/customer/customer_considered_checks";
    }

    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping("/fragments/customer_running_projects")
    public String cabinetRunningProjects(Model model, Authentication auth) {
        model.addAttribute("runningProjects",
                projectsService.getRunningByCustomer(((User) auth.getPrincipal()).getId(), false));

        return "/fragments/customer/customer_running_projects";
    }

    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping("/fragments/customer_technical_tasks")
    public String cabinetTechnicalTasks(Model model, Authentication auth) {
        model.addAttribute("technicalTasks",
                technicalTasksService.getAllByCustomer(((User) auth.getPrincipal()).getId(), false));

        return "/fragments/customer/customer_technical_tasks";
    }

    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping("/fragments/customer_complete_projects")
    public String cabinetCompleteProjects(Model model, Authentication auth) {
        model.addAttribute("completeProjects",
                projectsService.getCompleteByCustomer(((User) auth.getPrincipal()).getId(), false));

        return "fragments/customer/customer_complete_projects";
    }

    @ModelAttribute
    @PreAuthorize("hasAuthority('Customer')")
    public void addAttributes(Model model, Authentication auth) {

        model.addAttribute("customer", customersService.getById(((User) auth.getPrincipal()).getId()));
        model.addAttribute("specializations", DeveloperSpecialization.values());
        model.addAttribute("ranks", DeveloperRank.values());
    }
}
