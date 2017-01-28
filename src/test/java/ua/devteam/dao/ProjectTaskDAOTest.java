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
import ua.devteam.entity.tasks.ProjectTask;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.EntityUtils.getValidProjectTask;
import static ua.devteam.dao.DAOTestUtils.*;
import static ua.devteam.entity.enums.Status.COMPLETE;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class ProjectTaskDAOTest {

    private final String tableName = "project_tasks";
    private long testId;
    private ProjectTask testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProjectTaskDAO projectTaskDAO;

    @Before
    public void before() {
        testId = countRowsInTable(jdbcTemplate, tableName);
        testData = getValidProjectTask();
        testData.setRequestsForDevelopers(null);
        testData.setTasksDevelopmentData(null);
    }

    @Test
    public void createTest() {
        long id = createEntityWithIdTest(projectTaskDAO, testData, jdbcTemplate, tableName);
        testData.setId(id);

        assertEquals(testData, projectTaskDAO.getById(id));
    }

    @Test(expected = NullPointerException.class)
    public void createEmptyDeveloperTest() {
        projectTaskDAO.create(new ProjectTask());
    }

    @Test
    public void updateTest() {
        ProjectTask oldData = projectTaskDAO.getById(testId);
        testData.setId(++testId);
        testData.setTotalHoursSpent(oldData.getTotalHoursSpent());

        updateEntityTest(projectTaskDAO, oldData, testData, jdbcTemplate, tableName);
        assertEquals(testData, projectTaskDAO.getById(testData.getId()));
    }

    @Test
    public void deleteTest() {
        testData.setId(testId);

        deleteEntityTest(projectTaskDAO, testData, jdbcTemplate, tableName);
    }

    @Test
    public void checkStatusTest() {
        /*Because of stored procedures is kind of unable to implement by right way on H2 engine -
        just test for no exceptions*/

        projectTaskDAO.checkStatus(1L);
    }

    @Test
    public void setStatusByProjectTest() {
        projectTaskDAO.setStatusByProject(COMPLETE, 2L);

        List<ProjectTask> result = projectTaskDAO.getByProject(2L);
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.stream()
                        .filter(task -> task.getTaskStatus().equals(COMPLETE))
                        .count(),
                is((long) result.size()));
    }

    @Test
    public void getByIdTest() {
        ProjectTask data = getEntityByIdTest(projectTaskDAO, testId);

        assertThat(data.getId(), is(testId));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdWrongIdTest() {
        projectTaskDAO.getById(++testId);
    }

    @Test
    public void getByProjectIdTest() {
        List<ProjectTask> data = projectTaskDAO.getByProject(1L);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(task -> task.getProjectId() == 1L)
                        .count(),
                is((long) data.size()));
    }
}
