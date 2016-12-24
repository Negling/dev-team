package ua.devteam.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.configuration.DataAccessConfiguration;
import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.ProjectTask;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.dao.DAOTestUtils.*;


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
        testData = new ProjectTask((long) 1, "test", "test", Status.Running, 0);
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

    @Test(expected = DataIntegrityViolationException.class)
    public void updateNullIDTest() {
        projectTaskDAO.update(testData, testData);
    }

    @Test
    public void deleteTest() {
        testData.setId(testId);

        deleteEntityTest(projectTaskDAO, testData, jdbcTemplate, tableName);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deleteNullIDTest() {
        projectTaskDAO.delete(testData);
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
        List<ProjectTask> data = projectTaskDAO.getByProject((long) 1);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.get(0).getProjectId(), is((long) 1));
    }
}
