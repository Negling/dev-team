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
import static ua.devteam.entity.enums.CheckStatus.AWAITING;

@RunWith(JUnit4.class)
public class CheckServiceTest {

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
}
