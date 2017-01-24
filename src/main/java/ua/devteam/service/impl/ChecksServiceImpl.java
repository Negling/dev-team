package ua.devteam.service.impl;

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

@Service("checksService")
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ChecksServiceImpl implements ChecksService {

    private CheckDAO checkDAO;
    private ProjectsService projectsService;

    @Autowired
    public ChecksServiceImpl(CheckDAO checkDAO, ProjectsService projectsService) {
        this.checkDAO = checkDAO;
        this.projectsService = projectsService;
    }

    @Override
    public void registerCheck(Check check) {
        check.setStatus(AWAITING);

        checkDAO.create(check);
        projectsService.confirmProject(check.getProjectId());
    }

    @Override
    public void accept(Long projectId) {
        Check check = checkDAO.getByProject(projectId);

        check.setStatus(PAID);

        projectsService.runProject(projectId);
        checkDAO.update(check, check);
    }

    @Override
    public void decline(Long projectId) {
        Check check = checkDAO.getByProject(projectId);

        check.setStatus(DECLINED);

        projectsService.cancel(projectId);
        checkDAO.update(check, check);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Check> getNewByCustomer(Long customerId) {
        return checkDAO.getNewByCustomer(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Check> getCompleteByCustomer(Long customerId) {
        return checkDAO.getCompleteByCustomer(customerId);
    }
}
