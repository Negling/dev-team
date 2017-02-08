package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import ua.devteam.entity.enums.Role;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

import java.util.ResourceBundle;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.devteam.controllers.WebTestUtils.*;

@RunWith(JUnit4.class)
public class ManagersCabinetControllerTest {

    // service mocks
    private TechnicalTasksService technicalTasksService = mock(TechnicalTasksService.class);
    private ProjectsService projectsService = mock(ProjectsService.class);
    private ResourceBundle names = getViewNamingsBundle();
    private ResourceBundle mappings = getMappingBundle();

    // controller to test
    private ManagersCabinetController controller = new ManagersCabinetController(technicalTasksService, projectsService, getViewNamingsBundle());

    // controller mock object
    private MockMvc mockMvc = getConfiguredWithPlaceholdersStandaloneMockMvcBuilder(controller)
            .setCustomArgumentResolvers(getUserArgumentResolverWith(getUserWithIdAndRole(1L, Role.MANAGER)))
            .setViewResolvers(getDefaultViewResolver()).build();

    @Test
    public void getCabinetTest() throws Exception {
        mockMvc.perform(get(mappings.getString("manager.page")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.ranks"), names.getString("model.specializations")))
                .andExpect(view().name(names.getString("manager.page")));
    }

    @Test
    public void getManageTechnicalTasksFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("manager.technicalTasks")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.technicalTasks")))
                .andExpect(view().name(names.getString("manager.technicalTasks")));
    }

    @Test
    public void getFormProjectFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("manager.formProjects")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.pendingProjects")))
                .andExpect(view().name(names.getString("manager.formProjects")));
    }

    @Test
    public void getRunningProjectsFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("manager.runningProjects")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.runningProjects")))
                .andExpect(view().name(names.getString("manager.runningProjects")));
    }

    @Test
    public void getCompleteProjectsFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("manager.completeProjects")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.completeProjects")))
                .andExpect(view().name(names.getString("manager.completeProjects")));
    }
}
