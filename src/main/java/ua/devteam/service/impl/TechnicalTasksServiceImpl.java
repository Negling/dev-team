package ua.devteam.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.TechnicalTaskDAO;
import ua.devteam.entity.enums.Status;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.exceptions.InvalidObjectStateException;
import ua.devteam.service.OperationsService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TechnicalTasksService;

import java.util.List;

import static ua.devteam.entity.enums.Status.NEW;
import static ua.devteam.entity.enums.Status.PENDING;

@Service("technicalTasksService")
@Transactional(isolation = Isolation.READ_COMMITTED)
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
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void accept(Long technicalTaskId, Long managerId) {
        TechnicalTask technicalTask = technicalTaskDAO.getById(technicalTaskId);

        if (technicalTask.getStatus().equals(NEW)) {
            technicalTask.setStatus(PENDING);

            technicalTaskDAO.update(technicalTask, technicalTask);
            projectsService.createProject(technicalTask, managerId);
        } else {
            throw new InvalidObjectStateException("errorPage.alreadyAccepted", null);
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void decline(Long technicalTaskId, String managerCommentary) {
        TechnicalTask technicalTask = technicalTaskDAO.getById(technicalTaskId);

        if (technicalTask.getStatus().equals(NEW)) {
            technicalTask.setStatus(Status.DECLINED);

            if (managerCommentary != null && !managerCommentary.isEmpty()) {
                technicalTask.setManagerCommentary(managerCommentary);
            }

            technicalTaskDAO.update(technicalTask, technicalTask);
        } else {
            throw new InvalidObjectStateException("errorPage.alreadyDeclined", null);
        }
    }

    @Override
    public Long registerTechnicalTask(TechnicalTask task) {
        long resultId = technicalTaskDAO.create(task);
        task.setDeepId(resultId);

        operationsService.registerOperations(task.getOperations());

        return resultId;
    }

    @Override
    @Transactional(readOnly = true)
    public TechnicalTask getById(Long technicalTaskId, boolean loadNested) {
        if (loadNested) {
            return formTask(technicalTaskDAO.getById(technicalTaskId));
        }

        return technicalTaskDAO.getById(technicalTaskId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TechnicalTask> getAllUnassigned(boolean loadNested) {
        if (loadNested) {
            return formTask(technicalTaskDAO.getAllNew());
        }

        return technicalTaskDAO.getAllNew();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TechnicalTask> getAllByCustomer(Long customerId, boolean loadNested) {
        if (loadNested) {
            return formTask(technicalTaskDAO.getAllByCustomer(customerId));
        }

        return technicalTaskDAO.getAllByCustomer(customerId);
    }

    private TechnicalTask formTask(TechnicalTask technicalTask) {
        technicalTask.setOperations(operationsService.getByTechnicalTask(technicalTask.getId(), true));

        return technicalTask;
    }

    private List<TechnicalTask> formTask(List<TechnicalTask> tasks) {
        tasks.forEach(this::formTask);

        return tasks;
    }
}
