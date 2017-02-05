package ua.devteam.entity.tasks;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractBuilder;
import ua.devteam.entity.AbstractTask;
import ua.devteam.entity.Operation;
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

    private ProjectTask(Long projectId, Long operationId, String name, String description) {
        super(null, name, description);
        this.projectId = projectId;
        this.operationId = operationId;
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);
        tasksDevelopmentData.forEach(taskDeveloper -> taskDeveloper.setProjectTaskId(id));
    }

    public static class Builder extends AbstractBuilder<ProjectTask> {
        public Builder(Long projectId, Long operationId, String name, String description) {
            super(new ProjectTask(projectId, operationId, name, description));
        }

        public Builder setTotalHoursSpent(Integer totalHoursSpent) {
            return perform(() -> getConstruction().setTotalHoursSpent(totalHoursSpent));
        }

        public Builder setProjectId(Long projectId) {
            return perform(() -> getConstruction().setProjectId(projectId));
        }

        public Builder setRequestsForDevelopers(List<RequestForDevelopers> requestsForDevelopers) {
            return perform(() -> getConstruction().setRequestsForDevelopers(requestsForDevelopers));
        }

        public Builder setTasksDevelopmentData(List<TaskDevelopmentData> tasksDevelopmentData) {
            return perform(() -> getConstruction().setTasksDevelopmentData(tasksDevelopmentData));
        }

        public Builder setTaskStatus(Status taskStatus) {
            return perform(() -> getConstruction().setTaskStatus(taskStatus));
        }

        public Builder setId(Long id) {
            return perform(() -> getConstruction().setId(id));
        }

        @Override
        protected Builder perform(Operation operation) {
            operation.perform();
            return this;
        }
    }
}
