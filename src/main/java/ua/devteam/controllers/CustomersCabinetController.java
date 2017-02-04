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
import ua.devteam.service.ChecksService;
import ua.devteam.service.CustomersService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

import java.util.ResourceBundle;

/**
 * This controller processing GET requests to customers cabinet page and its fragments.
 */
@Controller
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CustomersCabinetController {

    private CustomersService customersService;
    private ChecksService checksService;
    private ProjectsService projectsService;
    private TechnicalTasksService technicalTasksService;
    private ResourceBundle pagesProperties;

    /**
     * Returns customers cabinet main page.
     */
    @GetMapping("${customer.page}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public String cabinet(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.customer"), customersService.getById(currentUser.getId()));
        model.addAttribute(pagesProperties.getString("model.specializations"), DeveloperSpecialization.values());
        model.addAttribute(pagesProperties.getString("model.ranks"), DeveloperRank.values());

        return pagesProperties.getString("customer.page");
    }


    /**
     * Returns customers cabinet new checks section.
     */
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("${customer.newChecks}")
    public String cabinetNewChecks(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.newChecks"), checksService.getNewByCustomer(currentUser.getId()));

        return pagesProperties.getString("customer.newChecks");
    }

    /**
     * Returns customers cabinet considered checks section.
     */
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("${customer.consideredChecks}")
    public String cabinetConsideredChecks(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.completeChecks"), checksService.getCompleteByCustomer(currentUser.getId()));

        return pagesProperties.getString("customer.consideredChecks");
    }

    /**
     * Returns customers cabinet running projects section.
     */
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("${customer.runningProjects}")
    public String cabinetRunningProjects(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.runningProjects"),
                projectsService.getRunningByCustomer(currentUser.getId(), false));

        return pagesProperties.getString("customer.runningProjects");
    }

    /**
     * Returns customers cabinet technical tasks section.
     */
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("${customer.technicalTasks}")
    public String cabinetTechnicalTasks(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.technicalTasks"),
                technicalTasksService.getAllByCustomer(currentUser.getId(), false));

        return pagesProperties.getString("customer.technicalTasks");
    }

    /**
     * Returns customers cabinet complete projects section.
     */
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("${customer.completeProjects}")
    public String cabinetCompleteProjects(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute(pagesProperties.getString("model.completeProjects"),
                projectsService.getCompleteByCustomer(currentUser.getId(), false));

        return pagesProperties.getString("customer.completeProjects");
    }
}
