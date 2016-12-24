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
import ua.devteam.entity.users.Developer;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static ua.devteam.dao.DAOTestUtils.*;
import static ua.devteam.entity.enums.DeveloperRank.Senior;
import static ua.devteam.entity.enums.DeveloperSpecialization.*;
import static ua.devteam.entity.enums.DeveloperStatus.*;
import static ua.devteam.entity.enums.Role.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class DeveloperDAOTest {

    private final String tableName = "developers";
    private long testId;
    private  Developer testData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DeveloperDAO developerDAO;

    @Before
    public void before() {
        testId = countRowsInTable(jdbcTemplate, tableName);
        testData = new Developer("test", "test", "test", "test", "test", Developer, (long) 0, new BigDecimal("700.00"),
                Backend, Senior, Available);
    }

    @Test
    public void createTest() {
        long id = createEntityWithIdTest(developerDAO, testData, jdbcTemplate, tableName);
        testData.setId(id);

        assertEquals(testData, developerDAO.getById(id));
    }

    @Test(expected = NullPointerException.class)
    public void createEmptyDeveloperTest() {
        developerDAO.create(new Developer());
    }

    @Test
    public void updateTest() {
        Developer oldData = developerDAO.getById(testId);
        testData.setId(++testId);

        updateEntityTest(developerDAO, oldData, testData, jdbcTemplate, tableName);
        assertEquals(testData, developerDAO.getById(testData.getId()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateNullIDTest() {
        developerDAO.update(testData, testData);
    }

    @Test(expected = NullPointerException.class)
    public void updateNullFieldsTest() {
        Developer data = new Developer();

        developerDAO.update(data, data);
    }

    @Test
    public void deleteTest() {
        testData.setId(testId);

        deleteEntityTest(developerDAO, testData, jdbcTemplate, tableName);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deleteNullIDTest() {
        developerDAO.delete(testData);
    }

    @Test
    public void getByIdTest() {
        Developer data = getEntityByIdTest(developerDAO, testId);

        assertThat(data.getId(), is(testId));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdWrongIdTest() {
        developerDAO.getById(++testId);
    }

    @Test
    public void getByEmailTest() {
        String email = developerDAO.getById(testId).getEmail();
        Developer data = developerDAO.getByEmail(email);

        assertThat(data, is(notNullValue()));
        assertThat(data.getEmail(), is(email));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdWrongEmailTest() {
        developerDAO.getByEmail("notAnEmailAtAll");
    }

    @Test
    public void getByParamsTest() {
        Developer data = developerDAO.getById(testId);

        List<Developer> result = developerDAO.getByParams(data.getSpecialization(), data.getRank());

        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.get(0).getSpecialization(), is(data.getSpecialization()));
        assertThat(result.get(0).getRank(), is(data.getRank()));
    }

    @Test(expected = NullPointerException.class)
    public void getByNullParamsTest() {
        developerDAO.getByParams(null, null);
    }

    @Test
    public void getByParamsWithLastnameTest() {
        Developer data = developerDAO.getById(testId);

        List<Developer> result = developerDAO.getByParams(data.getSpecialization(), data.getRank(),
                data.getLastName().substring(0, 1));

        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.get(0).getSpecialization(), is(data.getSpecialization()));
        assertThat(result.get(0).getRank(), is(data.getRank()));
        assertThat(result.get(0).getLastName().charAt(0), is(data.getLastName().charAt(0)));
    }

    @Test(expected = NullPointerException.class)
    public void getByNullParamsWithLastnameTest() {
        developerDAO.getByParams(null, null, null);
    }

    @Test
    public void getByLastnameWhichNotExist() {
        Developer data = developerDAO.getById(testId);

        List<Developer> result = developerDAO.getByParams(data.getSpecialization(), data.getRank(), "Это вообще не фамилия");

        assertThat(result.size(), is(0));
    }
}
