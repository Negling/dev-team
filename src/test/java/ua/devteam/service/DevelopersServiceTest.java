package ua.devteam.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.devteam.dao.DeveloperDAO;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.users.Developer;
import ua.devteam.exceptions.InvalidObjectStateException;
import ua.devteam.service.impl.DevelopersServiceImpl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static ua.devteam.EntityUtils.getValidDeveloper;
import static ua.devteam.entity.enums.DeveloperStatus.*;

@RunWith(JUnit4.class)
public class DevelopersServiceTest {

    private final long TEST_ID = 1;
    private DeveloperDAO developerDAO = mock(DeveloperDAO.class);
    private DevelopersService service = new DevelopersServiceImpl(developerDAO);
    private Developer testDev;

    @Before
    public void setUp() throws Exception {
        testDev = getValidDeveloper();
        testDev.setId(TEST_ID);

        when(developerDAO.getById(TEST_ID)).thenReturn(testDev);
    }

    @Test
    public void lockDeveloperTest() {
        testDev.setStatus(AVAILABLE);

        service.lockDeveloper(TEST_ID);

        verify(developerDAO, times(2)).getById(TEST_ID);
        verify(developerDAO, times(1)).update(testDev, testDev);
        assertThat(testDev.getStatus(), is(LOCKED));
    }

    @Test(expected = InvalidObjectStateException.class)
    public void notAvailableDeveloperLockTest() {
        testDev.setStatus(LOCKED);

        service.lockDeveloper(TEST_ID);
    }

    @Test
    public void unlockDeveloperTest() {
        testDev.setStatus(LOCKED);

        service.unlockDeveloper(TEST_ID);

        verify(developerDAO, times(2)).getById(TEST_ID);
        verify(developerDAO, times(1)).update(testDev, testDev);
        assertThat(testDev.getStatus(), is(AVAILABLE));
    }

    @Test(expected = InvalidObjectStateException.class)
    public void notLockedDeveloperLockTest() {
        testDev.setStatus(AVAILABLE);

        service.unlockDeveloper(TEST_ID);
    }

    @Test
    public void removeDevelopersFromProjectTest() {
        service.removeDevelopersFromProject(TEST_ID);

        verify(developerDAO, only()).updateStatusByProject(AVAILABLE, TEST_ID);
    }

    @Test
    public void approveDevelopersFromProjectTest() {
        service.approveDevelopersOnProject(TEST_ID);

        verify(developerDAO, only()).updateStatusByProject(HIRED, TEST_ID);
    }

    @Test
    public void getDeveloperByIdTest() {
        service.getById(TEST_ID);

        verify(developerDAO, only()).getById(TEST_ID);
    }

    @Test
    public void getAvailableDevsEmptyLastNameTest() {
        service.getAvailableDevelopers(DeveloperSpecialization.BACKEND, DeveloperRank.JUNIOR, "");

        verify(developerDAO, only()).getAvailableByParams(DeveloperSpecialization.BACKEND, DeveloperRank.JUNIOR);
    }

    @Test
    public void getAvailableDevsNullLastNameTest() {
        service.getAvailableDevelopers(DeveloperSpecialization.BACKEND, DeveloperRank.JUNIOR, null);

        verify(developerDAO, only()).getAvailableByParams(DeveloperSpecialization.BACKEND, DeveloperRank.JUNIOR);
    }

    @Test
    public void getAvailableDevsTest() {
        service.getAvailableDevelopers(DeveloperSpecialization.BACKEND, DeveloperRank.JUNIOR, "Doe");

        verify(developerDAO, only()).getAvailableByParams(DeveloperSpecialization.BACKEND, DeveloperRank.JUNIOR, "Doe");
    }
}
