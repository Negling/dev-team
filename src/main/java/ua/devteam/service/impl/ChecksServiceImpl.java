package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.devteam.dao.CheckDAO;
import ua.devteam.entity.users.Check;
import ua.devteam.service.ChecksService;
import ua.devteam.service.ProjectsService;

import java.util.List;

import static ua.devteam.entity.enums.CheckStatus.*;

@Service("checksService")
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
        check.setStatus(Awaiting);

        checkDAO.create(check);
        projectsService.confirmProject(check.getProjectId());
    }

    @Override
    public void accept(Long projectId) {
        Check check = checkDAO.getByProject(projectId);

        check.setStatus(Paid);

        projectsService.runProject(projectId);
        checkDAO.update(check, check);
    }

    @Override
    public void decline(Long projectId) {
        Check check = checkDAO.getByProject(projectId);

        check.setStatus(Cancelled);

        projectsService.cancel(projectId);
        checkDAO.update(check, check);
    }

    @Override
    public List<Check> getNewByCustomer(Long customerId) {
        return checkDAO.getNewByCustomer(customerId);
    }

    @Override
    public List<Check> getCompleteByCustomer(Long customerId) {
        return checkDAO.getCompleteByCustomer(customerId);
    }
}
