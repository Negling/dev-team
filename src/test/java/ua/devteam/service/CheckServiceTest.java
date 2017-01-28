package ua.devteam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.devteam.dao.CheckDAO;
import ua.devteam.entity.projects.Check;
import ua.devteam.service.impl.ChecksServiceImpl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static ua.devteam.EntityUtils.getValidCheck;
import static ua.devteam.entity.enums.CheckStatus.*;

@RunWith(JUnit4.class)
public class CheckServiceTest {

    private final long TEST_ID = 1;
    private ProjectsService projectsService = mock(ProjectsService.class);
    private CheckDAO checkDAO = mock(CheckDAO.class);
    private ChecksService service = new ChecksServiceImpl(checkDAO, projectsService);

    @Test
    public void registerCheckTest() {
        Check check = new Check();

        service.registerCheck(check);

        assertThat(check.getStatus(), is(AWAITING));
        verify(checkDAO, times(1)).create(check);
        verify(projectsService, times(1)).confirmProject(check.getProjectId());
    }

    @Test
    public void acceptTest() {
        Check check = getValidCheck();
        when(checkDAO.getByProject(TEST_ID)).thenReturn(check);

        service.accept(TEST_ID);

        assertThat(check.getStatus(), is(PAID));
        verify(checkDAO, times(1)).update(check, check);
        verify(projectsService, times(1)).runProject(TEST_ID);
    }

    @Test
    public void declineTest() {
        Check check = getValidCheck();
        when(checkDAO.getByProject(TEST_ID)).thenReturn(check);

        service.decline(TEST_ID);

        assertThat(check.getStatus(), is(DECLINED));
        verify(checkDAO, times(1)).update(check, check);
        verify(projectsService, times(1)).cancel(TEST_ID);
    }
}
