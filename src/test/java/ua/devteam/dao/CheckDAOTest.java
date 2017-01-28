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
import ua.devteam.entity.projects.Check;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.EntityUtils.getValidCheck;
import static ua.devteam.dao.DAOTestUtils.deleteEntityTest;
import static ua.devteam.dao.DAOTestUtils.updateEntityTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class CheckDAOTest {
    private final String tableName = "checks";
    private Check testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CheckDAO checkDAO;

    @Before
    public void before() {
        testData = getValidCheck();
    }

    @Test
    public void createTest() {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);
        long projectId = (long) countRowsInTable(jdbcTemplate, "projects");

        testData.setProjectId(projectId);
        checkDAO.create(testData);

        Check result = checkDAO.getByProject(projectId);
        testData.setProjectName(result.getProjectName());

        assertThat(++beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));
        assertThat(testData, is(result));
    }

    @Test
    public void updateTest() {
        Check oldData = checkDAO.getByProject(1L);

        updateEntityTest(checkDAO, oldData, testData, jdbcTemplate, tableName);
        testData.setProjectName(oldData.getProjectName());

        assertEquals(testData, checkDAO.getByProject(testData.getProjectId()));
    }

    @Test
    public void deleteTest() {
        deleteEntityTest(checkDAO, testData, jdbcTemplate, tableName);
    }

    @Test
    public void getByProjectTest() {
        Check data = checkDAO.getByProject(1L);

        assertThat(data, is(notNullValue()));
        assertThat(data.getProjectId(), is(1L));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByProjectWrongIdTest() {
        checkDAO.getByProject(0L);
    }

    @Test
    public void getNewByCustomerTest() {
        checkDAO.create(testData);

        List<Check> data = checkDAO.getNewByCustomer(1L);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
    }

    @Test
    public void getCompleteByCustomerTest() {
        List<Check> data = checkDAO.getCompleteByCustomer(1L);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
    }

    @Test
    public void getByCustomerTest() {
        List<Check> data = checkDAO.getAllByCustomer(1L);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
    }

    @Test
    public void getByCustomerWrongIdTest() {
        assertThat(checkDAO.getAllByCustomer(0L).size(), is(0));
    }
}
