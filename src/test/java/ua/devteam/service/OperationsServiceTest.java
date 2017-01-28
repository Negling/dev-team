package ua.devteam.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.devteam.EntityUtils;
import ua.devteam.dao.OperationDAO;
import ua.devteam.dao.RequestsForDevelopersDAO;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.service.impl.OperationsServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class OperationsServiceTest {

    private long ID_FOR_TEST = 1;
    private OperationDAO operationDAO = mock(OperationDAO.class);
    private RequestsForDevelopersDAO requestsForDevelopersDAO = mock(RequestsForDevelopersDAO.class);
    private OperationsService service = new OperationsServiceImpl(operationDAO, requestsForDevelopersDAO);
    private Operation operation;

    @Before
    public void setUp() throws Exception {
        operation = EntityUtils.getValidOperation();
    }

    @Test
    public void registerOperationTest() {
        service.registerOperation(operation);

        verify(operationDAO, times(1)).create(operation);
        verify(requestsForDevelopersDAO, times(operation.getRequestsForDevelopers().size())).create(any());
    }

    @Test
    public void registerOperationsTest() {
        List<Operation> operations = new ArrayList<Operation>() {{
            add(operation);
        }};

        service.registerOperations(operations);

        verify(operationDAO, times(operations.size())).create(any());
    }

    @Test
    public void getByTechnicalTaskNoNestedTest() {
        service.getByTechnicalTask(ID_FOR_TEST, false);

        verify(operationDAO, only()).getByTechnicalTask(ID_FOR_TEST);
        verify(requestsForDevelopersDAO, never()).getByOperation(anyLong());
    }

    @Test
    public void getByTechnicalTaskTest() {
        when(operationDAO.getByTechnicalTask(ID_FOR_TEST)).thenReturn(new ArrayList<Operation>() {{
            add(operation);
        }});

        service.getByTechnicalTask(ID_FOR_TEST, true);

        verify(operationDAO, only()).getByTechnicalTask(ID_FOR_TEST);
        verify(requestsForDevelopersDAO, atLeastOnce()).getByOperation(anyLong());
    }
}
