package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.devteam.entity.enums.Role;
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
public class ManagersCabinetControllerTest {

    // service mocks
    private TechnicalTasksService technicalTasksService = mock(TechnicalTasksService.class);
    private ProjectsService projectsService = mock(ProjectsService.class);

    // principal for tests
    private Principal manager;

    // controller to test
    private ManagersCabinetController controller = new ManagersCabinetController(technicalTasksService, projectsService);

    // controller mock object
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(getDefaultViewResolver()).build();

    // mocks and principal setup
    {
        long testId = 1;

        manager = getUserWithIdAndRole(testId, Role.Customer);

        // default mocks behavior
        when(technicalTasksService.getAllUnassigned(true)).thenReturn(new ArrayList<>());
        when(projectsService.getNewByManager(testId, true)).thenReturn(new ArrayList<>());
        when(projectsService.getRunningByManager(testId, false)).thenReturn(new ArrayList<>());
        when(projectsService.getCompleteByManager(testId, false)).thenReturn(new ArrayList<>());
    }

    @Test
    public void getCabinetTest() throws Exception {
        mockMvc.perform(get("/manage"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ranks", "specializations"))
                .andExpect(view().name("management"));
    }

    @Test
    public void getManageTechnicalTasksFragmentTest() throws Exception {
        mockMvc.perform(get("/manage/fragments/manage_technical_tasks").principal(manager))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("technicalTasks"))
                .andExpect(view().name("/fragments/management/manage_technical_tasks"));
    }

    @Test
    public void getFormProjectFragmentTest() throws Exception {
        mockMvc.perform(get("/manage/fragments/manage_form_project").principal(manager))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pendingProjects"))
                .andExpect(view().name("/fragments/management/manage_form_project"));
    }

    @Test
    public void getRunningProjectsFragmentTest() throws Exception {
        mockMvc.perform(get("/manage/fragments/manage_running_projects").principal(manager))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("runningProjects"))
                .andExpect(view().name("/fragments/management/manage_running_projects"));
    }

    @Test
    public void getCompleteProjectsFragmentTest() throws Exception {
        mockMvc.perform(get("/manage/fragments/manage_complete_projects").principal(manager))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("completeProjects"))
                .andExpect(view().name("/fragments/management/manage_complete_projects"));
    }
}
