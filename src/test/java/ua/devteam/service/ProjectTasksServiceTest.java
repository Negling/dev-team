package ua.devteam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.devteam.dao.OperationDAO;
import ua.devteam.dao.ProjectTaskDAO;
import ua.devteam.dao.RequestsForDevelopersDAO;
import ua.devteam.dao.TaskDevelopmentDataDAO;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.entity.tasks.ProjectTask;
import ua.devteam.service.impl.ProjectTasksServiceImpl;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static ua.devteam.entity.enums.Status.PENDING;
import static ua.devteam.entity.enums.Status.RUNNING;

@RunWith(JUnit4.class)
public class ProjectTasksServiceTest {

    private final long ID_FOR_TEST = 1;
    private ProjectTaskDAO projectTaskDAO = mock(ProjectTaskDAO.class);
    private TaskDevelopmentDataDAO taskDevelopmentDataDAO = mock(TaskDevelopmentDataDAO.class);
    private OperationDAO operationDAO = mock(OperationDAO.class);
    private RequestsForDevelopersDAO requestsForDevelopersDAO = mock(RequestsForDevelopersDAO.class);
    private ProjectTasksService service
            = new ProjectTasksServiceImpl(projectTaskDAO, taskDevelopmentDataDAO, operationDAO, requestsForDevelopersDAO);

    @Test
    public void registerFromTechnicalTaskTest() {
        when(operationDAO.getByTechnicalTask(ID_FOR_TEST)).thenReturn(new ArrayList<Operation>() {{
            add(new Operation());
        }});

        service.registerFromTechnicalTask(ID_FOR_TEST, ID_FOR_TEST);

        verify(operationDAO, only()).getByTechnicalTask(ID_FOR_TEST);
        verify(projectTaskDAO, atLeastOnce()).create(any());
    }

    @Test
    public void checkStatusTest() {
        service.checkStatus(ID_FOR_TEST);

        verify(projectTaskDAO, only()).checkStatus(ID_FOR_TEST);
    }

    @Test
    public void runByProjectTest() {
        service.runByProject(ID_FOR_TEST);

        verify(projectTaskDAO, only()).setStatusByProject(RUNNING, ID_FOR_TEST);
    }

    @Test
    public void confirmByProjectTest() {
        service.confirmByProject(ID_FOR_TEST);

        verify(projectTaskDAO, only()).setStatusByProject(PENDING, ID_FOR_TEST);
    }

    @Test
    public void getAllByProjectNoNestedTest() {
        service.getAllByProject(ID_FOR_TEST, false);

        verify(projectTaskDAO, only()).getByProject(ID_FOR_TEST);
    }

    @Test
    public void getAllByProjectEmptyTaskDataTest() {
        when(taskDevelopmentDataDAO.getAllByTask(any())).thenThrow(new EmptyResultDataAccessException(1));
        when(projectTaskDAO.getByProject(ID_FOR_TEST)).thenReturn(new ArrayList<ProjectTask>() {{
            add(new ProjectTask());
        }});

        service.getAllByProject(ID_FOR_TEST, true);

        verify(projectTaskDAO, only()).getByProject(ID_FOR_TEST);
        verify(requestsForDevelopersDAO, atLeastOnce()).getByOperation(any());
        verify(taskDevelopmentDataDAO, atLeastOnce()).getAllByTask(any());
    }

    @Test
    public void getAllByProjectTest() {
        when(projectTaskDAO.getByProject(ID_FOR_TEST)).thenReturn(new ArrayList<ProjectTask>() {{
            add(new ProjectTask());
        }});

        service.getAllByProject(ID_FOR_TEST, true);

        verify(projectTaskDAO, only()).getByProject(ID_FOR_TEST);
        verify(requestsForDevelopersDAO, atLeastOnce()).getByOperation(any());
        verify(taskDevelopmentDataDAO, atLeastOnce()).getAllByTask(any());
    }
}
