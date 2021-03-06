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
import ua.devteam.entity.projects.TechnicalTask;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.EntityUtils.getValidTechnicalTask;
import static ua.devteam.dao.DAOTestUtils.*;
import static ua.devteam.entity.enums.Status.NEW;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class TechnicalTaskDAOTest {

    private final String tableName = "technical_tasks";
    private long testId;
    private TechnicalTask testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TechnicalTaskDAO technicalTaskDAO;

    @Before
    public void before() {
        testId = countRowsInTable(jdbcTemplate, tableName);
        testData = getValidTechnicalTask(1L);
        testData.setOperations(null);
    }

    @Test
    public void createTest() {
        long id = createEntityWithIdTest(technicalTaskDAO, testData, jdbcTemplate, tableName);
        testData.setId(id);

        assertEquals(testData, technicalTaskDAO.getById(id));
    }

    @Test
    public void createWithCommentaryTest() {
        testData.setManagerCommentary("test");

        long id = createEntityWithIdTest(technicalTaskDAO, testData, jdbcTemplate, tableName);
        testData.setId(id);

        assertEquals(testData, technicalTaskDAO.getById(id));
    }

    @Test(expected = NullPointerException.class)
    public void createEmptyDeveloperTest() {
        technicalTaskDAO.create(new TechnicalTask());
    }

    @Test
    public void updateTest() {
        TechnicalTask oldData = technicalTaskDAO.getById(testId);
        testData.setId(++testId);

        updateEntityTest(technicalTaskDAO, oldData, testData, jdbcTemplate, tableName);
        assertEquals(testData, technicalTaskDAO.getById(testData.getId()));
    }

    @Test
    public void deleteTest() {
        testData.setId(testId);

        deleteEntityTest(technicalTaskDAO, testData, jdbcTemplate, tableName);
    }

    @Test
    public void getByIdTest() {
        TechnicalTask data = getEntityByIdTest(technicalTaskDAO, testId);

        assertThat(data.getId(), is(testId));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdWrongIdTest() {
        technicalTaskDAO.getById(++testId);
    }


    @Test
    public void getAllTest() {
        List<TechnicalTask> data = technicalTaskDAO.getAll();

        assertThat(data.size(), is(greaterThan(0)));
    }

    @Test
    public void getAllNewTest() {
        List<TechnicalTask> data = technicalTaskDAO.getAllNew();

        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(task -> task.getStatus().equals(NEW))
                        .count(),
                is((long) data.size()));
    }

    @Test
    public void getAllByCustomerTest() {
        List<TechnicalTask> data = technicalTaskDAO.getAllByCustomer(1L);

        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(task -> task.getCustomerId() == 1L)
                        .count(),
                is((long) data.size()));
    }
}
