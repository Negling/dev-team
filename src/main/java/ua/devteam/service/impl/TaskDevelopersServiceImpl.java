package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.devteam.dao.TaskDevelopersDAO;
import ua.devteam.entity.tasks.TaskDeveloper;
import ua.devteam.service.DevelopersService;
import ua.devteam.service.TaskDevelopersService;

import static ua.devteam.entity.enums.Status.New;
import static ua.devteam.entity.enums.Status.Running;

@Service("taskDevelopersService")
public class TaskDevelopersServiceImpl implements TaskDevelopersService {

    private TaskDevelopersDAO taskDevelopersDAO;
    private DevelopersService developersService;

    @Autowired
    public TaskDevelopersServiceImpl(TaskDevelopersDAO taskDevelopersDAO, DevelopersService developersService) {
        this.taskDevelopersDAO = taskDevelopersDAO;
        this.developersService = developersService;
    }

    @Override
    public TaskDeveloper bindDeveloper(Long developerId, Long taskId) {
        taskDevelopersDAO.createDefault(new TaskDeveloper(taskId, developerId));
        developersService.lockDeveloper(developerId);

        return taskDevelopersDAO.getByTaskAndDeveloper(taskId, developerId);
    }

    @Override
    public void unbindDeveloper(Long developerId) {
        developersService.unlockDeveloper(developerId);
        taskDevelopersDAO.delete(taskDevelopersDAO.getByDeveloperAndStatus(developerId, New));
    }

    @Override
    public void dropByProject(Long projectId) {
        developersService.removeDevelopersFromProject(projectId);
        taskDevelopersDAO.deleteAllByProject(projectId);
    }

    @Override
    public void runByProject(Long projectId) {
        developersService.approveDevelopersOnProject(projectId);
        taskDevelopersDAO.setStatusByProject(Running, projectId);
    }
}
