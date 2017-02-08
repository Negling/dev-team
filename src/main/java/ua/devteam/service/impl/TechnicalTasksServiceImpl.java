package ua.devteam.service.impl;


import lombok.AllArgsConstructor;
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

/**
 * Provides service operations to {@link TechnicalTask technicalTask}.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TechnicalTasksServiceImpl implements TechnicalTasksService {

    private TechnicalTaskDAO technicalTaskDAO;
    private OperationsService operationsService;
    private ProjectsService projectsService;

    /**
     * Updates status of technical task to "PENDING" and creates instance of project based on this Technical Task.
     * Cuz of reason, that all new technical tasks are in common pool, we can face situation, when two or more managers
     * will accept single technical task. To prevent this, transaction level must be "SERIALIZABLE", this level simply
     * locks tables and prohibits parallel transactions to affected data.
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
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

    /**
     * Updates status of technical task to "DECLINED".
     * Cuz of reason, that all new technical tasks are in common pool, we can face situation, when two or more managers
     * will decline single technical task. To prevent this, transaction level must be "SERIALIZABLE", this level simply
     * locks tables and prohibits parallel transactions to affected data.
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
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

    /**
     * Registers technical task instance and its operations in storage.
     */
    @Override
    public Long registerTechnicalTask(TechnicalTask task) {
        long resultId = technicalTaskDAO.create(task);
        task.setDeepId(resultId);

        operationsService.registerOperations(task.getOperations());

        return resultId;
    }

    /**
     * Returns Technical Task instance which id match to requested.
     *
     * @param loadNested if false, loads only technical task data, otherwise loads operations data too.
     * @return technical task
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TechnicalTask getById(Long technicalTaskId, boolean loadNested) {
        if (loadNested) {
            return formTask(technicalTaskDAO.getById(technicalTaskId));
        }

        return technicalTaskDAO.getById(technicalTaskId);
    }

    /**
     * Returns list of technical tasks with status "NEW".
     *
     * @param loadNested if false, loads only technical task data, otherwise loads operations data too.
     * @return list of technical tasks, or empty list if no results found
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<TechnicalTask> getAllUnassigned(boolean loadNested) {
        if (loadNested) {
            return formTask(technicalTaskDAO.getAllNew());
        }

        return technicalTaskDAO.getAllNew();
    }

    /**
     * Returns list of all technical tasks which customer ID matches to requested.
     *
     * @param loadNested if false, loads only technical task data, otherwise loads operations data too.
     * @return list of technical tasks, or empty list if no results found
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<TechnicalTask> getAllByCustomer(Long customerId, boolean loadNested) {
        if (loadNested) {
            return formTask(technicalTaskDAO.getAllByCustomer(customerId));
        }

        return technicalTaskDAO.getAllByCustomer(customerId);
    }

    /**
     * Sets operations to technical task.
     */
    private TechnicalTask formTask(TechnicalTask technicalTask) {
        technicalTask.setOperations(operationsService.getByTechnicalTask(technicalTask.getId(), true));

        return technicalTask;
    }

    private List<TechnicalTask> formTask(List<TechnicalTask> tasks) {
        tasks.forEach(this::formTask);

        return tasks;
    }
}
