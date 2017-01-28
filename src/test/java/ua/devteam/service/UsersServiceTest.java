package ua.devteam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.devteam.dao.UsersDAO;
import ua.devteam.service.impl.UsersServiceImpl;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class UsersServiceTest {

    private UsersDAO usersDAO = mock(UsersDAO.class);
    private UsersService service = new UsersServiceImpl(usersDAO);

    @Test
    public void phoneAvailableTest() {
        service.isPhoneAvailable(null);

        verify(usersDAO, only()).persistsByPhone(null);
    }

    @Test
    public void emailAvailableTest() {
        service.isEmailAvailable(null);

        verify(usersDAO, only()).persistsByEmail(null);
    }

    @Test
    public void getUserByEmailTest() {
        service.loadUserByUsername(null);

        verify(usersDAO, only()).getUser(null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void getUserNotFoundTest() {
        when(usersDAO.getUser(null)).thenThrow(new EmptyResultDataAccessException(1));

        service.loadUserByUsername(null);
    }
}
