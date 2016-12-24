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
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.Customer;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.dao.DAOTestUtils.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class CustomerDAOTest {

    private final String tableName = "customers";
    private long testId;
    private Customer testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CustomerDAO customerDAO;

    @Before
    public void before() {
        testId = countRowsInTable(jdbcTemplate, tableName);
        testData = new Customer("test", "test", "test", "test", "test", Role.Customer);
    }

    @Test
    public void createTest() {
        long id = createEntityWithIdTest(customerDAO, testData, jdbcTemplate, tableName);
        testData.setId(id);

        assertEquals(testData, customerDAO.getById(id));
    }

    @Test(expected = NullPointerException.class)
    public void createEmptyCustomerTest() {
        customerDAO.create(new Customer());
    }

    @Test
    public void updateTest() {
        Customer oldData = customerDAO.getById(testId);
        testData.setId(++testId);

        updateEntityTest(customerDAO, oldData, testData, jdbcTemplate, tableName);
        assertEquals(testData, customerDAO.getById(testData.getId()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateNullIDTest() {
        customerDAO.update(testData, testData);
    }

    @Test
    public void deleteTest() {
        testData.setId(testId);

        deleteEntityTest(customerDAO, testData, jdbcTemplate, tableName);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deleteNullIDTest() {
        customerDAO.delete(testData);
    }

    @Test
    public void getByIdTest() {
        Customer data = getEntityByIdTest(customerDAO, testId);

        assertThat(data.getId(), is(testId));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdWrongIdTest() {
        customerDAO.getById(++testId);
    }

    @Test
    public void getByEmailTest() {
        String email = customerDAO.getById(testId).getEmail();
        Customer data = customerDAO.getByEmail(email);

        assertThat(data, is(notNullValue()));
        assertThat(data.getEmail(), is(email));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdWrongEmailTest() {
        customerDAO.getByEmail("notAnEmailAtAll");
    }
}
