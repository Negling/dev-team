package ua.devteam.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.CheckDAO;
import ua.devteam.entity.projects.Check;
import ua.devteam.service.ChecksService;
import ua.devteam.service.ProjectsService;

import java.util.List;

import static ua.devteam.entity.enums.CheckStatus.*;

/**
 * Provides service operations to {@link Check check}.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ChecksServiceImpl implements ChecksService {

    private CheckDAO checkDAO;
    private ProjectsService projectsService;

    /**
     * Registers check with "AWAITING" status, and delegates to projects service subsequent operations.
     *
     * @param check to register
     */
    @Override
    public void registerCheck(Check check) {
        check.setStatus(AWAITING);

        checkDAO.create(check);
        projectsService.confirmProject(check.getProjectId());
    }

    /**
     * Changes status of check that bind to project with requested id to "PAID" and delegates to projects service
     * subsequent operations.
     *
     * @param projectId id of project that check is bind to.
     */
    @Override
    public void accept(Long projectId) {
        Check check = checkDAO.getByProject(projectId);

        check.setStatus(PAID);

        checkDAO.update(check, check);
        projectsService.runProject(projectId);
    }

    /**
     * Changes status of check that bind to project with requested id to "DECLINED" and delegates to projects service
     * subsequent operations.
     *
     * @param projectId id of project that check is bind to.
     */
    @Override
    public void decline(Long projectId) {
        Check check = checkDAO.getByProject(projectId);

        check.setStatus(DECLINED);

        checkDAO.update(check, check);
        projectsService.cancel(projectId);
    }

    /**
     * Returns list of checks that bind to specified customer and have "AWAITING" status.
     *
     * @param customerId id as query param
     * @return List of Checks or empty lis
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Check> getNewByCustomer(Long customerId) {
        return checkDAO.getNewByCustomer(customerId);
    }

    /**
     * Returns list of checks that bind to specified customer and have "PAID" or "DECLINED" status.
     *
     * @param customerId id as query param
     * @return List of Checks or empty lis
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Check> getCompleteByCustomer(Long customerId) {
        return checkDAO.getCompleteByCustomer(customerId);
    }
}
