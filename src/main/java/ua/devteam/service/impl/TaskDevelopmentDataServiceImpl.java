package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.TaskDevelopmentDataDAO;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.entity.users.Developer;
import ua.devteam.service.DevelopersService;
import ua.devteam.service.ProjectTasksService;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.List;

import static ua.devteam.entity.enums.Status.*;

@Service("taskDevelopersService")
@Transactional(isolation = Isolation.READ_COMMITTED)
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
        Developer dev = developersService.getById(developerId);

        taskDevelopmentDataDAO.createDefault(
                new TaskDevelopmentData(taskId, developerId, dev.getSpecialization(), dev.getRank()));
        developersService.lockDeveloper(developerId);

        return taskDevelopmentDataDAO.getByTaskAndDeveloper(taskId, developerId);
    }

    @Override
    public void unbindDeveloper(Long developerId) {
        developersService.unlockDeveloper(developerId);
        taskDevelopmentDataDAO.delete(
                taskDevelopmentDataDAO.getByDeveloperAndStatus(developerId, NEW).stream().findAny().orElse(null));
    }

    @Override
    public void dropByProject(Long projectId) {
        developersService.removeDevelopersFromProject(projectId);
        taskDevelopmentDataDAO.deleteAllByProject(projectId);
    }

    @Override
    public void runByProject(Long projectId) {
        developersService.approveDevelopersOnProject(projectId);
        taskDevelopmentDataDAO.setStatusByProject(RUNNING, projectId);
    }

    @Override
    public void confirmByProject(Long projectId) {
        taskDevelopmentDataDAO.setStatusByProject(PENDING, projectId);
    }

    @Override
    public void complete(Long taskId, Long developerId, Integer hoursSpent) {
        TaskDevelopmentData data = taskDevelopmentDataDAO.getByTaskAndDeveloper(taskId, developerId);
        data.setHoursSpent(hoursSpent);
        data.setStatus(COMPLETE);

        developersService.unlockDeveloper(developerId);
        taskDevelopmentDataDAO.update(data, data);
        projectTasksService.checkStatus(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDevelopmentData getActive(Long developerId) {
        return taskDevelopmentDataDAO.getByDeveloperAndStatus(developerId, RUNNING).stream().findAny().orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDevelopmentData> getComplete(Long developerId) {
        return taskDevelopmentDataDAO.getByDeveloperAndStatus(developerId, COMPLETE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDevelopmentData> getAllByDeveloper(Long developerId) {
        return taskDevelopmentDataDAO.getAllByDeveloper(developerId);
    }
}
