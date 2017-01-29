package ua.devteam.entity.tasks;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractTask;
import ua.devteam.entity.enums.Status;

import java.io.Serializable;
import java.util.List;

/**
 * Low-tier realization of abstract task class. Used as nested project object to describe operations that needs to be done.
 * Contains information about current development progress as task status, and information about hired developers and their
 * work status related to this task.
 * {@link ua.devteam.entity.projects.Project}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class ProjectTask extends AbstractTask implements Serializable {

    /* Id of project that this task is bound to */
    private Long projectId;
    /* Id of operation that this task is formed from */
    private Long operationId;
    /* Current task development status */
    private Status taskStatus;
    /* Amount of hours that all developers summary spent */
    private Integer totalHoursSpent;
    /* Information about hired developers and their task progress */
    private List<TaskDevelopmentData> tasksDevelopmentData;
    /* Information about requested developers by customer */
    private List<RequestForDevelopers> requestsForDevelopers;

    public ProjectTask(Long projectId, Operation operation) {
        this(null, projectId, operation.getId(), operation.getName(), operation.getDescription(), Status.NEW, 0, null,
                operation.getRequestsForDevelopers());
    }

    public ProjectTask(Long projectId, Long operationId, String name, String description, Status taskStatus,
                       Integer totalHoursSpent) {
        this(null, projectId, operationId, name, description, taskStatus, totalHoursSpent);
    }

    public ProjectTask(Long id, Long projectId, Long operationId, String name, String description, Status taskStatus,
                       Integer totalHoursSpent) {
        this(id, projectId, operationId, name, description, taskStatus, totalHoursSpent, null, null);
    }

    public ProjectTask(Long id, Long projectId, Long operationId, String name, String description, Status taskStatus,
                       Integer totalHoursSpent, List<TaskDevelopmentData> tasksDevelopmentData, List<RequestForDevelopers> requestsForDevelopers) {
        super(id, name, description);
        this.projectId = projectId;
        this.operationId = operationId;
        this.taskStatus = taskStatus;
        this.totalHoursSpent = totalHoursSpent;
        this.tasksDevelopmentData = tasksDevelopmentData;
        this.requestsForDevelopers = requestsForDevelopers;
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);
        tasksDevelopmentData.forEach(taskDeveloper -> taskDeveloper.setProjectTaskId(id));
    }
}
