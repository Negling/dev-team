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

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.EntityUtils.getValidTaskDevelopmentData;
import static ua.devteam.dao.DAOTestUtils.deleteEntityTest;
import static ua.devteam.dao.DAOTestUtils.updateEntityTest;
import static ua.devteam.entity.enums.Status.COMPLETE;

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
        testData = getValidTaskDevelopmentData();
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
        TaskDevelopmentData oldData = taskDevelopmentDataDAO.getByTaskAndDeveloper(2L, 1L);
        testData.setDeveloperId(10L);

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
        TaskDevelopmentData result = taskDevelopmentDataDAO.getByTaskAndDeveloper(1L, 1L);

        assertThat(result, is(notNullValue()));
        assertThat(result.getProjectTaskId(), is(1L));
        assertThat(result.getDeveloperId(), is(1L));
    }

    @Test
    public void getAllByTaskTest() {
        List<TaskDevelopmentData> result = taskDevelopmentDataDAO.getAllByTask(1L);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.stream()
                        .filter(tdd -> tdd.getProjectTaskId() == 1L)
                        .count(),
                is((long) result.size()));
    }

    @Test
    public void setStatusByProjectTest() {
        taskDevelopmentDataDAO.setStatusByProject(COMPLETE, 2L);

        List<TaskDevelopmentData> result = taskDevelopmentDataDAO.getAllByTask(2L);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.stream()
                        .filter(tdd -> tdd.getStatus().equals(COMPLETE))
                        .count(),
                is((long) result.size()));
    }

    @Test
    public void deleteAllByProjectTest() {
        taskDevelopmentDataDAO.deleteAllByProject(2L);

        List<TaskDevelopmentData> result = taskDevelopmentDataDAO.getAllByTask(2L);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllByDeveloperTest() {
        List<TaskDevelopmentData> result = taskDevelopmentDataDAO.getAllByDeveloper(1L);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(2));
        assertThat(result.stream()
                        .filter(taskDD -> taskDD.getDeveloperId() == 1L)
                        .count(),
                is((long) result.size()));
    }

    @Test
    public void getAllByDeveloperAndStatusTest() {
        List<TaskDevelopmentData> result = taskDevelopmentDataDAO.getByDeveloperAndStatus(1L, COMPLETE);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.stream()
                        .filter(taskDD -> taskDD.getDeveloperId() == 1L
                                && taskDD.getStatus().equals(COMPLETE))
                        .count(),
                is((long) result.size()));

    }
}
