package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.devteam.dao.DeveloperDAO;
import ua.devteam.dao.TaskDevelopersDAO;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.enums.DeveloperStatus;
import ua.devteam.entity.tasks.TaskDeveloper;
import ua.devteam.entity.users.Developer;
import ua.devteam.service.DevelopersService;

import java.util.List;

@Service("developersService")
public class DevelopersServiceImpl implements DevelopersService {

    private DeveloperDAO developerDAO;
    private TaskDevelopersDAO taskDevelopersDAO;

    @Autowired
    public DevelopersServiceImpl(DeveloperDAO developerDAO, TaskDevelopersDAO taskDevelopersDAO) {
        this.developerDAO = developerDAO;
        this.taskDevelopersDAO = taskDevelopersDAO;
    }

    @Override
    public Developer bind(Long developerId, Long taskId) {
        Developer dev = developerDAO.getById(developerId);
        dev.setStatus(DeveloperStatus.Locked);

        taskDevelopersDAO.createDefault(new TaskDeveloper(taskId, developerId));
        developerDAO.update(dev, dev);

        return dev;
    }

    @Override
    public void unbind(Long developerId) {
        Developer dev = developerDAO.getById(developerId);
        TaskDeveloper taskDev = taskDevelopersDAO.getByTaskAndDeveloper(dev.getCurrentTaskId(), developerId);

        dev.setCurrentTaskId(null);
        dev.setStatus(DeveloperStatus.Available);

        taskDevelopersDAO.delete(taskDev);
        developerDAO.update(dev, dev);
    }

    @Override
    public List<Developer> getAvailableDevelopers(DeveloperSpecialization specialization, DeveloperRank rank, String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return developerDAO.getAvailableByParams(specialization, rank);
        } else {
            return developerDAO.getAvailableByParams(specialization, rank, lastName);
        }
    }
}
