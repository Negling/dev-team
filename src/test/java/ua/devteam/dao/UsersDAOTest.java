package ua.devteam.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.configuration.DataAccessConfiguration;
import ua.devteam.entity.users.User;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class UsersDAOTest {
    @Autowired
    private UsersDAO usersDAO;

    @Test
    public void testByCustomerEmail() {
        validateUser(usersDAO.getUser("test@test.test"));
    }

    @Test
    public void testByManagerEmail() {
        validateUser(usersDAO.getUser("manager@gmail.com"));
    }

    @Test
    public void testByDeveloperEmail() {
        validateUser(usersDAO.getUser("dev1@gmail.com"));
    }

    @Test
    public void persistsByEmailTrueTest() {
        assertTrue(usersDAO.persistsByEmail("test@test.test"));
    }

    @Test
    public void persistsByEmailFalseTest() {
        assertFalse(usersDAO.persistsByEmail("test@test.com"));
    }

    @Test
    public void persistsByPhoneTrueTest() {
        assertTrue(usersDAO.persistsByPhone("+380454345493"));
    }

    @Test
    public void persistsByPhoneFalseTest() {
        assertFalse(usersDAO.persistsByPhone("+380454345494"));
    }

    private void validateUser(User user) {
        assertThat(user.getId(), is(notNullValue()));
        assertThat(user.getFirstName(), is(notNullValue()));
        assertThat(user.getLastName(), is(notNullValue()));
        assertThat(user.getEmail(), is(notNullValue()));
        assertThat(user.getPassword(), is(notNullValue()));
        assertThat(user.getRole(), is(notNullValue()));
    }
}
