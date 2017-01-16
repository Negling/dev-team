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
import ua.devteam.entity.tasks.RequestForDevelopers;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.dao.DAOTestUtils.deleteEntityTest;
import static ua.devteam.dao.DAOTestUtils.updateEntityTest;
import static ua.devteam.entity.enums.DeveloperRank.Junior;
import static ua.devteam.entity.enums.DeveloperSpecialization.Backend;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class RequestsForDevelopersDAOTest {
    private final String tableName = "requests_for_developers";
    private RequestForDevelopers testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RequestsForDevelopersDAO requestsForDevelopersDAO;

    @Before
    public void before() {
        testData = new RequestForDevelopers((long) 1, Backend, Junior, 1);
    }

    @Test
    public void createTest() {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);
        requestsForDevelopersDAO.create(testData);

        assertThat(++beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));
    }

    @Test(expected = NullPointerException.class)
    public void createEmptyTest() {
        requestsForDevelopersDAO.create(new RequestForDevelopers());
    }

    @Test
    public void updateTest() {
        RequestForDevelopers oldData = requestsForDevelopersDAO.getByOperation((long) 1).get(0);
        testData.setOperation_id((long) 2);

        updateEntityTest(requestsForDevelopersDAO, oldData, testData, jdbcTemplate, tableName);

        assertTrue(requestsForDevelopersDAO.getByOperation((long) 2).contains(testData));
    }

    @Test(expected = NullPointerException.class)
    public void updateEmptyTest() {
        RequestForDevelopers data = new RequestForDevelopers();

        requestsForDevelopersDAO.update(data, data);
    }

    @Test
    public void deleteTest() {
        deleteEntityTest(requestsForDevelopersDAO, testData, jdbcTemplate, tableName);
    }

    @Test(expected = NullPointerException.class)
    public void deleteEmptyTest() {
        requestsForDevelopersDAO.delete(new RequestForDevelopers());
    }

    @Test
    public void getByOperationTest() {
        List<RequestForDevelopers> data = requestsForDevelopersDAO.getByOperation((long) 1);

        assertThat(data, is(notNullValue()));
        assertThat(data.size(), is(greaterThan(0)));
        assertThat(data.stream()
                        .filter(rfd -> rfd.getOperation_id() == (long) 1)
                        .count(),
                is((long) data.size()));
    }
}
