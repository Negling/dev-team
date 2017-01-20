package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.Customer;
import ua.devteam.service.ChecksService;
import ua.devteam.service.CustomersService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

import java.security.Principal;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.devteam.controllers.WebTestUtils.getDefaultViewResolver;
import static ua.devteam.controllers.WebTestUtils.getUserWithIdAndRole;

@RunWith(JUnit4.class)
public class CustomersCabinetControllerTest {

    // service mocks
    private CustomersService customersService = mock(CustomersService.class);
    private ChecksService checksService = mock(ChecksService.class);
    private ProjectsService projectsService = mock(ProjectsService.class);
    private TechnicalTasksService technicalTasksService = mock(TechnicalTasksService.class);

    // principal for tests
    private Principal customer;

    // controller to test
    private CustomersCabinetController controller = new CustomersCabinetController(customersService, checksService,
            projectsService, technicalTasksService);

    // controller mock object
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(getDefaultViewResolver()).build();

    // mocks and principal setup
    {
        long testId = 1;

        customer = getUserWithIdAndRole(testId, Role.Customer);

        // default mocks behavior
        when(customersService.getById(testId)).thenReturn(new Customer());
        when(checksService.getNewByCustomer(testId)).thenReturn(new ArrayList<>());
        when(checksService.getCompleteByCustomer(testId)).thenReturn(new ArrayList<>());
        when(projectsService.getRunningByCustomer(testId, false)).thenReturn(new ArrayList<>());
        when(projectsService.getCompleteByCustomer(testId, false)).thenReturn(new ArrayList<>());
        when(technicalTasksService.getAllByCustomer(testId, false)).thenReturn(new ArrayList<>());
    }

    @Test
    public void getCabinetTest() throws Exception {
        mockMvc.perform(get("/cabinet").principal(customer))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer", "specializations", "ranks"))
                .andExpect(view().name("customers-cabinet"));
    }

    @Test
    public void getNewChecksFragmentTest() throws Exception {
        mockMvc.perform(get("/cabinet/fragments/customer_new_checks").principal(customer))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("newChecks"))
                .andExpect(view().name("/fragments/customer/customer_new_checks"));
    }

    @Test
    public void getConsideredChecksFragmentTest() throws Exception {
        mockMvc.perform(get("/cabinet/fragments/customer_considered_checks").principal(customer))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("completeChecks"))
                .andExpect(view().name("/fragments/customer/customer_considered_checks"));
    }

    @Test
    public void getRunningProjectsFragmentTest() throws Exception {
        mockMvc.perform(get("/cabinet/fragments/customer_running_projects").principal(customer))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("runningProjects"))
                .andExpect(view().name("/fragments/customer/customer_running_projects"));
    }

    @Test
    public void getTechnicalTasksFragmentTest() throws Exception {
        mockMvc.perform(get("/cabinet/fragments/customer_technical_tasks").principal(customer))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("technicalTasks"))
                .andExpect(view().name("/fragments/customer/customer_technical_tasks"));
    }

    @Test
    public void getCompleteProjectsFragmentTest() throws Exception {
        mockMvc.perform(get("/cabinet/fragments/customer_complete_projects").principal(customer))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("completeProjects"))
                .andExpect(view().name("fragments/customer/customer_complete_projects"));
    }
}
