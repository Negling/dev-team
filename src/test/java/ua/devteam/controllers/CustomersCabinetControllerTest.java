package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.Customer;
import ua.devteam.service.ChecksService;
import ua.devteam.service.CustomersService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.devteam.controllers.WebTestUtils.*;

@RunWith(JUnit4.class)
public class CustomersCabinetControllerTest {

    // service mocks
    private CustomersService customersService = mock(CustomersService.class);
    private ChecksService checksService = mock(ChecksService.class);
    private ProjectsService projectsService = mock(ProjectsService.class);
    private TechnicalTasksService technicalTasksService = mock(TechnicalTasksService.class);

    // controller to test
    private CustomersCabinetController controller = new CustomersCabinetController(customersService, checksService,
            projectsService, technicalTasksService, getPagesBundle());

    // controller mock object
    private MockMvc mockMvc;

    // setup
    {
        long testId = 1;

        // default mocks behavior
        when(customersService.getById(testId)).thenReturn(new Customer());

        mockMvc = getConfiguredWithPlaceholdersStandaloneMockMvcBuilder(controller)
                .setCustomArgumentResolvers(getUserArgumentResolverWith(getUserWithIdAndRole(testId, Role.CUSTOMER)))
                .setViewResolvers(getDefaultViewResolver())
                .build();
    }

    @Test
    public void getCabinetTest() throws Exception {
        mockMvc.perform(get("/cabinet"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer", "specializations", "ranks"))
                .andExpect(view().name("customers-cabinet"));
    }

    @Test
    public void getNewChecksFragmentTest() throws Exception {
        mockMvc.perform(get("/cabinet/fragments/customer_new_checks"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("newChecks"))
                .andExpect(view().name("/fragments/customer/customer_new_checks"));
    }

    @Test
    public void getConsideredChecksFragmentTest() throws Exception {
        mockMvc.perform(get("/cabinet/fragments/customer_considered_checks"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("completeChecks"))
                .andExpect(view().name("/fragments/customer/customer_considered_checks"));
    }

    @Test
    public void getRunningProjectsFragmentTest() throws Exception {
        mockMvc.perform(get("/cabinet/fragments/customer_running_projects"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("runningProjects"))
                .andExpect(view().name("/fragments/customer/customer_running_projects"));
    }

    @Test
    public void getTechnicalTasksFragmentTest() throws Exception {
        mockMvc.perform(get("/cabinet/fragments/customer_technical_tasks"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("technicalTasks"))
                .andExpect(view().name("/fragments/customer/customer_technical_tasks"));
    }

    @Test
    public void getCompleteProjectsFragmentTest() throws Exception {
        mockMvc.perform(get("/cabinet/fragments/customer_complete_projects"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("completeProjects"))
                .andExpect(view().name("/fragments/customer/customer_complete_projects"));
    }
}
