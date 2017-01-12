package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.devteam.dao.TaskDevelopmentDataDAO;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.service.DevelopersService;
import ua.devteam.service.ProjectTasksService;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.List;

import static ua.devteam.entity.enums.Status.*;

@Service("taskDevelopersService")
public class TaskDevelopmentDataServiceImpl implements TaskDevelopmentDataService {

    private TaskDevelopmentDataDAO taskDevelopmentDataDAO;
    private DevelopersService developersService;
    private ProjectTasksService projectTasksService;

    @Autowired
    public TaskDevelopmentDataServiceImpl(TaskDevelopmentDataDAO taskDevelopmentDataDAO, DevelopersService developersService,
                                          ProjectTasksService projectTasksService) {
        this.taskDevelopmentDataDAO = taskDevelopmentDataDAO;
        this.developersService = developersService;
        this.projectTasksService = projectTasksService;
    }

    @Override
    public TaskDevelopmentData bindDeveloper(Long developerId, Long taskId) {
        taskDevelopmentDataDAO.createDefault(new TaskDevelopmentData(taskId, developerId));
        developersService.lockDeveloper(developerId);

        return taskDevelopmentDataDAO.getByTaskAndDeveloper(taskId, developerId);
    }

    @Override
    public void unbindDeveloper(Long developerId) {
        developersService.unlockDeveloper(developerId);
        taskDevelopmentDataDAO.delete(
                taskDevelopmentDataDAO.getByDeveloperAndStatus(developerId, New).stream().findAny().orElse(null));
    }

    @Override
    public void dropByProject(Long projectId) {
        developersService.removeDevelopersFromProject(projectId);
        taskDevelopmentDataDAO.deleteAllByProject(projectId);
    }

    @Override
    public void runByProject(Long projectId) {
        developersService.approveDevelopersOnProject(projectId);
        taskDevelopmentDataDAO.setStatusByProject(Running, projectId);
    }

    @Override
    public void complete(Long taskId, Long developerId, Integer hoursSpent) {
        TaskDevelopmentData data = taskDevelopmentDataDAO.getByTaskAndDeveloper(taskId, developerId);
        data.setHoursSpent(hoursSpent);
        data.setStatus(Complete);

        developersService.unlockDeveloper(developerId);
        taskDevelopmentDataDAO.update(data, data);
        projectTasksService.checkStatus(taskId);
    }

    @Override
    public TaskDevelopmentData getActive(Long developerId) {
        return taskDevelopmentDataDAO.getByDeveloperAndStatus(developerId, Running).stream().findAny().orElse(null);
    }

    @Override
    public List<TaskDevelopmentData> getComplete(Long developerId) {
        return taskDevelopmentDataDAO.getByDeveloperAndStatus(developerId, Complete);
    }
}
