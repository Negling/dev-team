package ua.devteam.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.devteam.dao.TechnicalTaskDAO;
import ua.devteam.entity.enums.Status;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.service.OperationsService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

import java.util.List;

import static ua.devteam.entity.enums.Status.Running;

@Service("technicalTasksService")
public class TechnicalTasksServiceImpl implements TechnicalTasksService {

    private TechnicalTaskDAO technicalTaskDAO;
    private OperationsService operationsService;
    private ProjectsService projectsService;

    @Autowired
    public TechnicalTasksServiceImpl(TechnicalTaskDAO technicalTaskDAO, OperationsService operationsService,
                                     ProjectsService projectsService) {
        this.technicalTaskDAO = technicalTaskDAO;
        this.operationsService = operationsService;
        this.projectsService = projectsService;
    }

    @Override
    public void accept(Long technicalTaskId, Long managerId) {
        TechnicalTask technicalTask = technicalTaskDAO.getById(technicalTaskId);
        technicalTask.setStatus(Running);

        technicalTaskDAO.update(technicalTask, technicalTask);
        projectsService.createProject(technicalTask, managerId);
    }

    @Override
    public void decline(Long technicalTaskId, String managerCommentary) {
        TechnicalTask technicalTask = technicalTaskDAO.getById(technicalTaskId);
        technicalTask.setStatus(Status.Declined);
        technicalTask.setManagerCommentary(managerCommentary.isEmpty() ? null : managerCommentary);

        technicalTaskDAO.update(technicalTask, technicalTask);
    }

    @Override
    public Long registerTechnicalTask(TechnicalTask task) {
        long resultId = technicalTaskDAO.create(task);
        task.setDeepId(resultId);

        operationsService.registerOperations(task.getOperations());

        return resultId;
    }

    @Override
    public List<TechnicalTask> getAllUnassigned(boolean loadNested) {
        if (loadNested) {
            return formTask(technicalTaskDAO.getAllNew());
        }

        return technicalTaskDAO.getAllNew();
    }

    @Override
    public List<TechnicalTask> getAllTechnicalTasks(boolean loadNested) {
        if (loadNested) {
            return formTask(technicalTaskDAO.getAll());
        }

        return technicalTaskDAO.getAll();
    }


    private List<TechnicalTask> formTask(List<TechnicalTask> tasks) {
        tasks.forEach(technicalTask ->
                technicalTask.setOperations(operationsService.getByTechnicalTask(technicalTask.getId(), true)));

        return tasks;
    }
}
