package ua.devteam.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.ProjectDAO;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.service.ProjectTasksService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.Date;
import java.util.List;

import static ua.devteam.entity.enums.Status.*;

/**
 * Provides service operations to {@link Project project}.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ProjectsServiceImpl implements ProjectsService {

    private ProjectDAO projectDAO;
    private ProjectTasksService projectTasksService;
    private TaskDevelopmentDataService taskDevelopersService;

    /**
     * Creates and records Project instance from specified Technical task, and delegates to project tasks service subsequent operations.
     *
     * @return generated ID
     */
    @Override
    public long createProject(TechnicalTask technicalTask, Long managerId) {
        long projectId = projectDAO.create(
                new Project.Builder(
                        technicalTask.getName(),
                        technicalTask.getDescription(),
                        technicalTask.getCustomerId(),
                        managerId)
                        .build()
        );

        projectTasksService.registerFromTechnicalTask(technicalTask.getId(), projectId);

        return projectId;
    }

    /**
     * Updates projects status to "DECLINED", endDate to current time, and delegates to task developers service subsequent operations.
     */
    @Override
    public void decline(Long projectId, String managerCommentary) {
        Project project = projectDAO.getById(projectId);
        project.setEndDate(new Date());

        if (managerCommentary != null && !managerCommentary.isEmpty()) {
            project.setManagerCommentary(managerCommentary);
        }

        projectDAO.update(project, project);
        projectDAO.updateStatus(projectId, DECLINED);
        taskDevelopersService.dropByProject(projectId);
    }

    /**
     * Updates project, project tasks and task developers data status to "PENDING".
     */
    @Override
    public void confirmProject(Long projectId) {
        projectTasksService.confirmByProject(projectId);
        taskDevelopersService.confirmByProject(projectId);
        projectDAO.updateStatus(projectId, PENDING);
    }

    /**
     * Updates project, project tasks and task developers data status to "RUNNING".
     */
    @Override
    public void runProject(Long projectId) {
        projectTasksService.runByProject(projectId);
        taskDevelopersService.runByProject(projectId);
        projectDAO.updateStatus(projectId, RUNNING);
    }


    /**
     * Updates project, project tasks status to "CANCELED" Sets project end date to current time.
     * Deletes project task developers data.
     */
    @Override
    public void cancel(Long projectId) {
        Project project = projectDAO.getById(projectId);
        project.setEndDate(new Date());

        projectTasksService.cancelByProject(projectId);
        taskDevelopersService.dropByProject(projectId);
        projectDAO.update(project, project);
        projectDAO.updateStatus(projectId, CANCELED);
    }

    /**
     * Returns project instance which id match to requested.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return projects instance
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Project getById(Long projectId, boolean loadNested) {
        Project project = projectDAO.getById(projectId);

        if (loadNested) {
            project.setTasks(projectTasksService.getAllByProject(project.getId(), true));
        }

        return project;
    }

    /**
     * Returns list of projects, which manager ID match to requested and status is "NEW" or "PENDING", or empty list if no results found.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return list of projects or empty list
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Project> getNewByManager(Long managerId, boolean loadNested) {
        List<Project> projects = projectDAO.getByManagerAndStatus(managerId, NEW);

        if (loadNested) {
            projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId(), true)));
        }

        return projects;
    }

    /**
     * Returns list of projects, which manager ID match to requested and status is "RUNNING" or "PENDING", or empty list if no results found.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return list of projects or empty list
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Project> getRunningByManager(Long managerId, boolean loadNested) {
        List<Project> projects = projectDAO.getRunningByManager(managerId);

        if (loadNested) {
            projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId(), true)));
        }

        return projects;
    }

    /**
     * Returns list of projects, which manager ID match to requested and status is "COMPLETE", "CANCELED" or "DECLINED",
     * or empty list if no results found.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return list of projects or empty list
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Project> getCompleteByManager(Long managerId, boolean loadNested) {
        List<Project> projects = projectDAO.getCompleteByManager(managerId);

        if (loadNested) {
            projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId(), true)));
        }

        return projects;
    }

    /**
     * Returns list of projects, which customer ID match to requested and status is "RUNNING" or "PENDING", or empty list if no results found.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return list of projects or empty list
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Project> getRunningByCustomer(Long customerId, boolean loadNested) {
        List<Project> projects = projectDAO.getRunningByCustomer(customerId);

        if (loadNested) {
            projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId(), true)));
        }

        return projects;
    }

    /**
     * Returns list of projects, which customer ID match to requested and status is "COMPLETE", "CANCELED" or "DECLINED",
     * or empty list if no results found.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return list of projects or empty list
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Project> getCompleteByCustomer(Long customerId, boolean loadNested) {
        List<Project> projects = projectDAO.getCompleteByCustomer(customerId);

        if (loadNested) {
            projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId(), true)));
        }

        return projects;
    }
}
