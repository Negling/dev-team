package ua.devteam.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.configuration.DataAccessConfiguration;
import ua.devteam.entity.tasks.TaskDevelopmentData;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.dao.DAOTestUtils.deleteEntityTest;
import static ua.devteam.dao.DAOTestUtils.updateEntityTest;
import static ua.devteam.entity.enums.DeveloperRank.Junior;
import static ua.devteam.entity.enums.DeveloperSpecialization.Backend;
import static ua.devteam.entity.enums.Status.Complete;
import static ua.devteam.entity.enums.Status.Running;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class TaskDevelopmentDataDAOTest {
    private final String tableName = "task_development_data";
    private TaskDevelopmentData testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TaskDevelopmentDataDAO taskDevelopmentDataDAO;

    @Before
    public void before() {
        testData = new TaskDevelopmentData((long) 1, "test", "test", (long) 1, "test", "test", Backend, Junior,
                new BigDecimal("0.00"), 1, Running);
    }

    @Test
    public void createTest() {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);

        taskDevelopmentDataDAO.create(testData);

        assertThat(++beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));
    }

    @Test(expected = NullPointerException.class)
    public void createEmptyTest() {
        taskDevelopmentDataDAO.create(new TaskDevelopmentData());
    }

    @Test
    public void createDefaultTest() {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);

        taskDevelopmentDataDAO.createDefault(testData);

        assertThat(++beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));
    }

    @Test(expected = NullPointerException.class)
    public void createDefaultEmptyTest() {
        taskDevelopmentDataDAO.createDefault(new TaskDevelopmentData());
    }

    @Test
    public void updateTest() {
        TaskDevelopmentData oldData = taskDevelopmentDataDAO.getByTaskAndDeveloper((long) 2, (long) 1);
        testData.setDeveloperId((long) 10);

        updateEntityTest(taskDevelopmentDataDAO, oldData, testData, jdbcTemplate, tableName);

        TaskDevelopmentData result = taskDevelopmentDataDAO.getByTaskAndDeveloper(testData.getProjectTaskId(), testData.getDeveloperId());

        assertThat(result.getHoursSpent(), is(testData.getHoursSpent()));
        assertThat(result.getStatus(), is(testData.getStatus()));
    }

    @Test(expected = NullPointerException.class)
    public void updateEmptyTest() {
        TaskDevelopmentData data = new TaskDevelopmentData();

        taskDevelopmentDataDAO.update(data, data);
    }

    @Test
    public void deleteTest() {
        deleteEntityTest(taskDevelopmentDataDAO, testData, jdbcTemplate, tableName);
    }

    @Test
    public void getByTaskAndDeveloperTest() {
        TaskDevelopmentData result = taskDevelopmentDataDAO.getByTaskAndDeveloper((long) 1, (long) 1);

        assertThat(result, is(notNullValue()));
        assertThat(result.getProjectTaskId(), is((long) 1));
        assertThat(result.getDeveloperId(), is((long) 1));
    }

    @Test
    public void getAllByTaskTest() {
        List<TaskDevelopmentData> result = taskDevelopmentDataDAO.getAllByTask((long) 1);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.stream()
                        .filter(tdd -> tdd.getProjectTaskId() == (long) 1)
                        .count(),
                is((long) result.size()));
    }

    @Test
    public void setStatusByProjectTest() {
        taskDevelopmentDataDAO.setStatusByProject(Complete, (long) 2);

        List<TaskDevelopmentData> result = taskDevelopmentDataDAO.getAllByTask((long) 2);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.stream()
                        .filter(tdd -> tdd.getStatus().equals(Complete))
                        .count(),
                is((long) result.size()));
    }

    @Test
    public void deleteAllByProjectTest() {
        taskDevelopmentDataDAO.deleteAllByProject((long) 2);

        List<TaskDevelopmentData> result = taskDevelopmentDataDAO.getAllByTask((long) 2);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllByDeveloperTest() {
        List<TaskDevelopmentData> result = taskDevelopmentDataDAO.getAllByDeveloper((long) 1);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(2));
        assertThat(result.stream()
                        .filter(taskDD -> taskDD.getDeveloperId() == (long) 1)
                        .count(),
                is((long) result.size()));
    }

    @Test
    public void getAllByDeveloperAndStatusTest() {
        List<TaskDevelopmentData> result = taskDevelopmentDataDAO.getByDeveloperAndStatus((long) 1, Complete);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.stream()
                        .filter(taskDD -> taskDD.getDeveloperId() == (long) 1
                                && taskDD.getStatus().equals(Complete))
                        .count(),
                is((long) result.size()));

    }
}
