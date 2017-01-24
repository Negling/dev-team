package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.service.*;
import ua.devteam.validation.entityValidators.CheckValidator;
import ua.devteam.validation.entityValidators.ProjectTaskValidator;
import ua.devteam.validation.entityValidators.ProjectValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.devteam.EntityUtils.*;
import static ua.devteam.controllers.WebTestUtils.*;
import static ua.devteam.entity.enums.DeveloperRank.JUNIOR;
import static ua.devteam.entity.enums.DeveloperSpecialization.BACKEND;

@RunWith(JUnit4.class)
public class ManagersCabinetRestControllerTest {

    // service mocks
    private TechnicalTasksService technicalTasksService = mock(TechnicalTasksService.class);
    private ProjectsService projectsService = mock(ProjectsService.class);
    private ChecksService checksService = mock(ChecksService.class);
    private DevelopersService developersService = mock(DevelopersService.class);
    private TaskDevelopmentDataService taskDevelopmentDataService = mock(TaskDevelopmentDataService.class);

    // necessary data
    private Validator projectValidator = new ProjectValidator(new ProjectTaskValidator());

    // controller to test
    private ManagersCabinetRestController controller = new ManagersCabinetRestController(getDefaultMessageSource(),
            technicalTasksService, projectsService, checksService, taskDevelopmentDataService, developersService, projectValidator);

    private MockMvc mockMvc;

    // setup
    {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(getDefaultViewResolver()).setValidator(new CheckValidator()).build();

        // default mocks behavior
        when(developersService.getAvailableDevelopers(any(), any(), any())).thenReturn(new ArrayList<>());
        when(taskDevelopmentDataService.bindDeveloper(anyLong(), anyLong())).thenReturn(new TaskDevelopmentData());
    }

    @Test
    public void getDevelopersTest() throws Exception {
        mockMvc.perform(get("/manage/getDevelopers")
                .param("specialization", BACKEND.toString())
                .param("rank", JUNIOR.toString())
                .param("lastName", "Kiral"))
                // checks
                .andExpect(status().isOk());

        verify(developersService, only()).getAvailableDevelopers(BACKEND, JUNIOR, "Kiral");
    }

    @Test
    public void bindDeveloperTest() throws Exception {
        Map<String, Long> requestBody = new HashMap<String, Long>() {{
            put("devId", (long) 1);
            put("taskId", (long) 1);
        }};

        mockMvc.perform(post("/manage/bind")
                .contentType("application/json")
                .content(getObjectAsJson(requestBody)))
                // checks
                .andExpect(status().isOk());

        verify(taskDevelopmentDataService, only()).bindDeveloper((long) 1, (long) 1);
    }

    @Test
    public void unbindDeveloperTest() throws Exception {
        mockMvc.perform(delete("/manage/unbind")
                .contentType("application/json")
                .content(getObjectAsJson(1)))
                // checks
                .andExpect(status().isOk());

        verify(taskDevelopmentDataService, only()).unbindDeveloper((long) 1);
    }

    @Test
    public void declineTechnicalTaskTest() throws Exception {
        Map<String, String> requestBody = new HashMap<String, String>() {{
            put("technicalTaskId", "1");
            put("managerCommentary", "test");
        }};

        mockMvc.perform(put("/manage/declineTechnicalTask")
                .contentType("application/json")
                .content(getObjectAsJson(requestBody)))
                // checks
                .andExpect(status().isOk());

        verify(technicalTasksService, only()).decline((long) 1, "test");
    }

    @Test
    public void formTechnicalTaskAsProjectTest() throws Exception {
        mockMvc.perform(post("/manage/formAsProject")
                .principal(getUserWithIdAndRole((long) 1, Role.ADMIN))
                .contentType("application/json")
                .content(getObjectAsJson(1)))
                // checks
                .andExpect(status().isOk());

        verify(technicalTasksService, only()).accept((long) 1, (long) 1);
    }

    @Test
    public void declineProjectTest() throws Exception {
        Map<String, String> requestBody = new HashMap<String, String>() {{
            put("projectId", "1");
            put("managerCommentary", "test");
        }};

        mockMvc.perform(put("/manage/declineProject")
                .contentType("application/json")
                .content(getObjectAsJson(requestBody)))
                // checks
                .andExpect(status().isOk());

        verify(projectsService, only()).decline((long) 1, "test");
    }

    @Test
    public void formProjectWithInvalidCheckTest() throws Exception {
        mockMvc.perform(put("/manage/accept")
                .locale(Locale.ENGLISH)
                .contentType("application/json")
                .content(getObjectAsJson(getInvalidCheck())))
                // checks
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void formProjectWithInvalidProjectStateTest() throws Exception {
        when(projectsService.getById(anyLong(), eq(true))).thenReturn(new Project());

        mockMvc.perform(put("/manage/accept")
                .locale(Locale.ENGLISH)
                .contentType("application/json")
                .content(getObjectAsJson(getValidCheck())))
                // checks
                .andExpect(status().isUnprocessableEntity());

        verify(projectsService, times(1)).getById(anyLong(), eq(true));
    }

    @Test
    public void formProjectSuccessTest() throws Exception {
        when(projectsService.getById(anyLong(), eq(true))).thenReturn(getValidProject());

        mockMvc.perform(put("/manage/accept")
                .locale(Locale.ENGLISH)
                .contentType("application/json")
                .content(getObjectAsJson(getValidCheck())))
                // checks
                .andExpect(status().isOk());

        verify(projectsService, times(1)).getById(anyLong(), eq(true));
        verify(checksService, times(1)).registerCheck(getValidCheck());
    }
}
