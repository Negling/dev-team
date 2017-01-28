package ua.devteam.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.configuration.DataAccessConfiguration;
import ua.devteam.entity.tasks.Operation;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.EntityUtils.getValidOperation;
import static ua.devteam.dao.DAOTestUtils.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class OperationDAOTest {

    private final String tableName = "technical_task_operations";
    private long testId;
    private Operation testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OperationDAO operationDAO;

    @Before
    public void before() {
        testId = countRowsInTable(jdbcTemplate, tableName);
        testData = getValidOperation();
        testData.setRequestsForDevelopers(null);
    }

    @Test
    public void createTest() {
        long id = createEntityWithIdTest(operationDAO, testData, jdbcTemplate, tableName);
        testData.setId(id);

        assertEquals(testData, operationDAO.getById(id));
    }

    @Test(expected = NullPointerException.class)
    public void createEmptyManagerTest() {
        operationDAO.create(new Operation());
    }

    @Test
    public void updateTest() {
        Operation oldData = operationDAO.getById(testId);
        testData.setId(++testId);

        updateEntityTest(operationDAO, oldData, testData, jdbcTemplate, tableName);
        assertEquals(testData, operationDAO.getById(testData.getId()));
    }

    @Test
    public void deleteTest() {
        testData.setId(testId);

        deleteEntityTest(operationDAO, testData, jdbcTemplate, tableName);
    }

    @Test
    public void getByIdTest() {
        Operation data = getEntityByIdTest(operationDAO, testId);
        assertThat(data.getId(), is(testId));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdWrongIdTest() {
        operationDAO.getById(++testId);
    }

    @Test
    public void getByTechnicalTaskTest() {
        List<Operation> data = operationDAO.getByTechnicalTask(1L);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(operation -> operation.getTechnicalTaskId() == 1L)
                        .count(),
                is((long) data.size()));
    }
}
