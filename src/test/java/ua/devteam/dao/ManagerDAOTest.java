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
import ua.devteam.entity.users.Manager;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.dao.DAOTestUtils.*;
import static ua.devteam.entity.enums.Role.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class ManagerDAOTest {

    private final String tableName = "managers";
    private long testId;
    private Manager testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ManagerDAO managerDAO;

    @Before
    public void before() {
        testId = countRowsInTable(jdbcTemplate, tableName);
        testData = new Manager("test", "test", "test", "test", "test", Manager, (long) 0);
    }

    @Test
    public void createTest() {
        long id = createEntityWithIdTest(managerDAO, testData, jdbcTemplate, tableName);
        testData.setId(id);

        assertEquals(testData, managerDAO.getById(id));
    }

    @Test(expected = NullPointerException.class)
    public void createEmptyManagerTest() {
        managerDAO.create(new Manager());
    }

    @Test
    public void updateTest() {
        Manager oldData = managerDAO.getById(testId);
        testData.setId(++testId);
        testData.setTotalProjectsServed(oldData.getTotalProjectsServed());

        updateEntityTest(managerDAO, oldData, testData, jdbcTemplate, tableName);
        assertEquals(testData, managerDAO.getById(testData.getId()));
    }

    @Test(expected = NullPointerException.class)
    public void updateNullFieldsTest() {
        Manager data = new Manager();

        managerDAO.update(data, data);
    }

    @Test
    public void deleteTest() {
        testData.setId(testId);

        deleteEntityTest(managerDAO, testData, jdbcTemplate, tableName);
    }

    @Test
    public void getByIdTest() {
        Manager data = getEntityByIdTest(managerDAO, testId);

        assertThat(data.getId(), is(testId));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdWrongIdTest() {
        managerDAO.getById(++testId);
    }

    @Test
    public void getByEmailTest() {
        String email = managerDAO.getById(testId).getEmail();
        Manager data = managerDAO.getByEmail(email);

        assertThat(data, is(notNullValue()));
        assertThat(data.getEmail(), is(email));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdWrongEmailTest() {
        managerDAO.getByEmail("notAnEmailAtAll");
    }
}
