package ua.devteam.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.devteam.dao.TechnicalTaskDAO;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.exceptions.InvalidObjectStateException;
import ua.devteam.service.impl.TechnicalTasksServiceImpl;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static ua.devteam.EntityUtils.getValidTechnicalTask;
import static ua.devteam.entity.enums.Status.*;

@RunWith(JUnit4.class)
public class TechnicalTasksServiceTest {

    private final long ID_FOR_TEST = 1;
    private TechnicalTaskDAO technicalTaskDAO = mock(TechnicalTaskDAO.class);
    private OperationsService operationsService = mock(OperationsService.class);
    private ProjectsService projectsService = mock(ProjectsService.class);
    private TechnicalTasksService service = new TechnicalTasksServiceImpl(technicalTaskDAO, operationsService, projectsService);
    private TechnicalTask task;

    @Before
    public void setUp() throws Exception {
        task = getValidTechnicalTask(ID_FOR_TEST);
        task.setStatus(NEW);

        when(technicalTaskDAO.getById(ID_FOR_TEST)).thenReturn(task);
    }

    @Test
    public void registerTechnicalTaskTest() {
        service.registerTechnicalTask(task);

        verify(technicalTaskDAO, only()).create(task);
        verify(operationsService, only()).registerOperations(task.getOperations());
    }

    @Test
    public void acceptTest() {
        service.accept(ID_FOR_TEST, ID_FOR_TEST);

        assertThat(task.getStatus(), is(PENDING));
        verify(technicalTaskDAO, times(1)).update(task, task);
        verify(projectsService, only()).createProject(task, ID_FOR_TEST);
    }

    @Test(expected = InvalidObjectStateException.class)
    public void acceptNotNewTest() {
        task.setStatus(PENDING);

        service.accept(ID_FOR_TEST, ID_FOR_TEST);
    }

    @Test
    public void declineTest() {
        service.decline(ID_FOR_TEST, "");

        assertThat(task.getStatus(), is(DECLINED));
        verify(technicalTaskDAO, times(1)).update(task, task);
    }

    @Test(expected = InvalidObjectStateException.class)
    public void declineNotNewTest() {
        task.setStatus(PENDING);

        service.decline(ID_FOR_TEST, "");
    }

    @Test
    public void getByIdNoNestedTest() {
        service.getById(ID_FOR_TEST, false);

        verify(technicalTaskDAO, only()).getById(ID_FOR_TEST);
        verify(operationsService, never()).getByTechnicalTask(any(), eq(true));
    }

    @Test
    public void getByIdTest() {
        service.getById(ID_FOR_TEST, true);

        verify(technicalTaskDAO, only()).getById(ID_FOR_TEST);
        verify(operationsService, atLeastOnce()).getByTechnicalTask(any(), eq(true));
    }

    @Test
    public void getAllUnassignedNoNestedTest() {
        service.getAllUnassigned(false);

        verify(technicalTaskDAO, only()).getAllNew();
        verify(operationsService, never()).getByTechnicalTask(any(), eq(true));
    }

    @Test
    public void getAllUnassignedTest() {
        when(technicalTaskDAO.getAllNew()).thenReturn(new ArrayList<TechnicalTask>() {{
            add(task);
        }});

        service.getAllUnassigned(true);

        verify(technicalTaskDAO, only()).getAllNew();
        verify(operationsService, atLeastOnce()).getByTechnicalTask(any(), eq(true));
    }

    @Test
    public void getAllByCustomerNoNestedTest() {
        service.getAllByCustomer(ID_FOR_TEST, false);

        verify(technicalTaskDAO, only()).getAllByCustomer(ID_FOR_TEST);
        verify(operationsService, never()).getByTechnicalTask(any(), eq(true));
    }

    @Test
    public void getAllByCustomerTest() {
        when(technicalTaskDAO.getAllByCustomer(ID_FOR_TEST)).thenReturn(new ArrayList<TechnicalTask>() {{
            add(task);
        }});

        service.getAllByCustomer(ID_FOR_TEST, true);

        verify(technicalTaskDAO, only()).getAllByCustomer(ID_FOR_TEST);
        verify(operationsService, atLeastOnce()).getByTechnicalTask(any(), eq(true));
    }
}
