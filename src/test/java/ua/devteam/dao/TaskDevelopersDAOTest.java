package ua.devteam.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.configuration.DataAccessConfiguration;
import ua.devteam.entity.tasks.TaskDeveloper;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.dao.DAOTestUtils.*;
import static ua.devteam.entity.enums.DeveloperRank.Junior;
import static ua.devteam.entity.enums.DeveloperSpecialization.Backend;
import static ua.devteam.entity.enums.Status.Running;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class TaskDevelopersDAOTest {
    private final String tableName = "task_developers";
    private TaskDeveloper testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TaskDevelopersDAO taskDevelopersDAO;

    @Before
    public void before() {
        testData = new TaskDeveloper((long) 1, (long) 1, "test", "test", Backend, Junior, new BigDecimal("0.00"), 1, Running);
    }

    @Test
    public void createTest() {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);

        taskDevelopersDAO.create(testData);

        assertThat(++beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));
    }

    @Test(expected = NullPointerException.class)
    public void createEmptyTest() {
        taskDevelopersDAO.create(new TaskDeveloper());
    }

    @Test
    public void createDefaultTest() {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);

        taskDevelopersDAO.createDefault(testData);

        assertThat(++beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createDefaultEmptyTest() {
        taskDevelopersDAO.createDefault(new TaskDeveloper());
    }

    @Test
    public void updateTest() {
        TaskDeveloper oldData = taskDevelopersDAO.getByTaskAndDeveloper((long) 2, (long) 1);
        testData.setDeveloperId((long) 10);

        updateEntityTest(taskDevelopersDAO, oldData, testData, jdbcTemplate, tableName);

        TaskDeveloper result = taskDevelopersDAO.getByTaskAndDeveloper(testData.getProjectTaskId(), testData.getDeveloperId());

        assertThat(result.getHoursSpent(), is(testData.getHoursSpent()));
        assertThat(result.getStatus(), is(testData.getStatus()));
    }

    @Test(expected = NullPointerException.class)
    public void updateEmptyTest() {
        TaskDeveloper data = new TaskDeveloper();

        taskDevelopersDAO.update(data, data);
    }

    @Test
    public void deleteTest() {
        deleteEntityTest(taskDevelopersDAO, testData, jdbcTemplate, tableName);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deleteEmptyTest() {
        taskDevelopersDAO.delete(new TaskDeveloper());
    }

    @Test
    public void getByTaskAndDeveloperTest() {
        TaskDeveloper result = taskDevelopersDAO.getByTaskAndDeveloper((long) 1, (long) 1);

        assertThat(result, is(notNullValue()));
        assertThat(result.getProjectTaskId(), is((long) 1));
        assertThat(result.getDeveloperId(), is((long) 1));
    }

    @Test
    public void getAllByTaskTest() {
        List<TaskDeveloper> result = taskDevelopersDAO.getAllByTask((long) 1);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.get(0).getProjectTaskId(), is((long) 1));
    }
}
