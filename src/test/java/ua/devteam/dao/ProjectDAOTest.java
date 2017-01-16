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
import ua.devteam.entity.projects.Project;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.dao.DAOTestUtils.*;
import static ua.devteam.entity.enums.Status.Complete;
import static ua.devteam.entity.enums.Status.Running;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class ProjectDAOTest {

    private final String tableName = "projects";
    private long testId;
    private Project testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProjectDAO projectDAO;

    @Before
    public void before() {
        testId = countRowsInTable(jdbcTemplate, tableName);
        testData = new Project("test", "test", (long) 1, (long) 1, null, (long) 1, null, new Date(), null, Running);
    }

    @Test
    public void createTest() {
        long id = createEntityWithIdTest(projectDAO, testData, jdbcTemplate, tableName);
        testData.setId(id);

        assertEquals(testData, projectDAO.getById(id));
    }

    @Test
    public void createWithCommentaryTest() {
        testData.setManagerCommentary("test");

        long id = createEntityWithIdTest(projectDAO, testData, jdbcTemplate, tableName);
        testData.setId(id);

        assertEquals(testData, projectDAO.getById(id));
    }

    @Test
    public void createWithEndDateTest() {
        testData.setEndDate(new Date());

        long id = createEntityWithIdTest(projectDAO, testData, jdbcTemplate, tableName);
        testData.setId(id);

        assertEquals(testData, projectDAO.getById(id));
    }

    @Test(expected = NullPointerException.class)
    public void createEmptyDeveloperTest() {
        projectDAO.create(new Project());
    }

    @Test
    public void updateTest() {
        Project oldData = projectDAO.getById(testId);
        testData.setId(++testId);

        updateEntityTest(projectDAO, oldData, testData, jdbcTemplate, tableName);
        assertEquals(testData, projectDAO.getById(testData.getId()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateNullIDTest() {
        projectDAO.update(testData, testData);
    }

    @Test
    public void deleteTest() {
        testData.setId(testId);

        deleteEntityTest(projectDAO, testData, jdbcTemplate, tableName);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deleteNullIDTest() {
        projectDAO.delete(testData);
    }

    @Test
    public void getByIdTest() {
        Project data = getEntityByIdTest(projectDAO, testId);

        assertThat(data.getId(), is(testId));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdWrongIdTest() {
        projectDAO.getById(++testId);
    }

    @Test
    public void updateStatusTest() {
        /*Because of stored procedures is kind of unable to implement by right way on H2 engine -
        just test for no exceptions*/

        projectDAO.updateStatus((long) 1, Running);
    }

    @Test
    public void getCompleteByManagerTest() {
        List<Project> data = projectDAO.getCompleteByManager((long) 1);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(project -> project.getStatus().equals(Complete)
                                && project.getManagerId() == (long) 1)
                        .count(),
                is((long) data.size()));
    }

    @Test
    public void getRunningByManagerTest() {
        List<Project> data = projectDAO.getRunningByManager((long) 2);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(project -> project.getStatus().equals(Running)
                                && project.getManagerId() == (long) 2)
                        .count(),
                is((long) data.size()));
    }

    @Test
    public void getCompleteByCustomerTest() {
        List<Project> data = projectDAO.getCompleteByCustomer((long) 1);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(project -> project.getStatus().equals(Complete)
                                && project.getCustomerId() == (long) 1)
                        .count(),
                is((long) data.size()));
    }

    @Test
    public void getRunningByCustomerTest() {
        List<Project> data = projectDAO.getRunningByCustomer((long) 2);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(project -> project.getStatus().equals(Running)
                                && project.getCustomerId() == (long) 2)
                        .count(),
                is((long) data.size()));
    }

    @Test
    public void getByManagerIDTest() {
        Long managerId = projectDAO.getById(testId).getManagerId();
        List<Project> data = projectDAO.getAllByManager(managerId);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(project -> project.getManagerId().equals(managerId))
                        .count(),
                is((long) data.size()));
    }

    @Test
    public void getByBadManagerIDTest() {
        long lastManagerId = countRowsInTable(jdbcTemplate, "managers");

        List<Project> data = projectDAO.getAllByManager(++lastManagerId);

        assertThat(data.size(), is(0));
    }

    @Test
    public void getByManagerAndStatusTest() {
        Project project = projectDAO.getById(testId);
        Status status = project.getStatus();
        long managerId = project.getManagerId();

        List<Project> data = projectDAO.getByManagerAndStatus(managerId, status);

        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(proj -> project.getStatus().equals(status)
                                && project.getManagerId().equals(managerId))
                        .count(),
                is((long) data.size()));
    }
}
