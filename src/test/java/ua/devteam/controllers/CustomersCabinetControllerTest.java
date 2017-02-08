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

import java.util.ResourceBundle;

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
    private ResourceBundle names = getViewNamingsBundle();
    private ResourceBundle mappings = getMappingBundle();

    // controller to test
    private CustomersCabinetController controller = new CustomersCabinetController(customersService, checksService,
            projectsService, technicalTasksService, getViewNamingsBundle());

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
        mockMvc.perform(get(mappings.getString("customer.page")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(
                        names.getString("model.customer"),
                        names.getString("model.specializations"),
                        names.getString("model.ranks")))
                .andExpect(view().name(names.getString("customer.page")));
    }

    @Test
    public void getNewChecksFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("customer.newChecks")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.newChecks")))
                .andExpect(view().name(names.getString("customer.newChecks")));
    }

    @Test
    public void getConsideredChecksFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("customer.consideredChecks")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.completeChecks")))
                .andExpect(view().name(names.getString("customer.consideredChecks")));
    }

    @Test
    public void getRunningProjectsFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("customer.runningProjects")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.runningProjects")))
                .andExpect(view().name(names.getString("customer.runningProjects")));
    }

    @Test
    public void getTechnicalTasksFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("customer.technicalTasks")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.technicalTasks")))
                .andExpect(view().name(names.getString("customer.technicalTasks")));
    }

    @Test
    public void getCompleteProjectsFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("customer.completeProjects")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.completeProjects")))
                .andExpect(view().name(names.getString("customer.completeProjects")));
    }
}
