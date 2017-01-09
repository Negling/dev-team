package ua.devteam.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.devteam.dao.OperationDAO;
import ua.devteam.dao.RequestsForDevelopersDAO;
import ua.devteam.dao.TechnicalTaskDAO;
import ua.devteam.entity.enums.Status;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

import java.util.List;

import static ua.devteam.entity.enums.Status.Running;

@Service("technicalTasksService")
public class TechnicalTasksServiceImpl implements TechnicalTasksService {

    private TechnicalTaskDAO technicalTaskDAO;
    private OperationDAO operationDAO;
    private RequestsForDevelopersDAO requestsForDevelopersDAO;
    private ProjectsService projectsService;

    @Autowired
    public TechnicalTasksServiceImpl(TechnicalTaskDAO technicalTaskDAO, OperationDAO operationDAO,
                                     RequestsForDevelopersDAO requestsForDevelopersDAO, ProjectsService projectsService) {
        this.technicalTaskDAO = technicalTaskDAO;
        this.operationDAO = operationDAO;
        this.requestsForDevelopersDAO = requestsForDevelopersDAO;
        this.projectsService = projectsService;
    }

    @Override
    public void accept(Long technicalTaskId, Long managerId) {
        TechnicalTask technicalTask = technicalTaskDAO.getById(technicalTaskId);
        technicalTask.setStatus(Running);

        technicalTaskDAO.update(technicalTask, technicalTask);
        projectsService.createProject(technicalTask, managerId);

        //TODO: refactor
    }

    @Override
    public void decline(Long technicalTaskId, String managerCommentary) {
        TechnicalTask data = technicalTaskDAO.getById(technicalTaskId);
        data.setStatus(Status.Declined);
        data.setManagerCommentary(managerCommentary.isEmpty() ? null : managerCommentary);

        technicalTaskDAO.update(data, data);
    }

    @Override
    public Long registerTechnicalTask(TechnicalTask task) {
        long resultId = technicalTaskDAO.create(task);
        task.setDeepId(resultId);

        task.getOperations().forEach(operation -> {
            operation.setDeepId(operationDAO.create(operation));

            operation.getRequestsForDevelopers().forEach(requestForDevelopers ->
                    requestsForDevelopersDAO.create(requestForDevelopers));
        });

        return resultId;
    }

    @Override
    public List<TechnicalTask> getAllUnassigned() {
        return formTask(technicalTaskDAO.getAllNew());
    }

    @Override
    public List<TechnicalTask> getAllTechnicalTasks() {
        return formTask(technicalTaskDAO.getAll());
    }


    private List<TechnicalTask> formTask(List<TechnicalTask> tasks) {
        tasks.forEach(technicalTask -> {
            technicalTask.setOperations(operationDAO.getByTechnicalTask(technicalTask.getId()));
            technicalTask.getOperations().forEach(operation ->
                    operation.setRequestsForDevelopers(requestsForDevelopersDAO.getByOperation(operation.getId())));
        });

        return tasks;
    }
}
