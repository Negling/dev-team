package ua.devteam.entity.tasks;


import ua.devteam.entity.AbstractTask;
import ua.devteam.entity.enums.Status;

import java.io.Serializable;
import java.util.List;

public class ProjectTask extends AbstractTask implements Serializable{
    private Long projectId;
    private Long operationId;
    private Status taskStatus;
    private Integer totalHoursSpent;
    private List<TaskDevelopmentData> tasksDevelopmentData;
    private List<RequestForDevelopers> requestsForDevelopers;

    public ProjectTask() {
    }

    public ProjectTask(Long projectId, Operation operation) {
        this(null, projectId, operation.getId(), operation.getName(), operation.getDescription(), Status.New, 0, null,
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getTotalHoursSpent() {
        return totalHoursSpent;
    }

    public void setTotalHoursSpent(Integer totalHoursSpent) {
        this.totalHoursSpent = totalHoursSpent;
    }

    public List<TaskDevelopmentData> getTasksDevelopmentData() {
        return tasksDevelopmentData;
    }

    public void setTasksDevelopmentData(List<TaskDevelopmentData> tasksDevelopmentData) {
        this.tasksDevelopmentData = tasksDevelopmentData;
    }

    public List<RequestForDevelopers> getRequestsForDevelopers() {
        return requestsForDevelopers;
    }

    public void setRequestsForDevelopers(List<RequestForDevelopers> requestsForDevelopers) {
        this.requestsForDevelopers = requestsForDevelopers;
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);

        tasksDevelopmentData.forEach(taskDeveloper -> taskDeveloper.setProjectTaskId(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectTask)) return false;
        if (!super.equals(o)) return false;

        ProjectTask that = (ProjectTask) o;

        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (operationId != null ? !operationId.equals(that.operationId) : that.operationId != null) return false;
        if (taskStatus != that.taskStatus) return false;
        if (totalHoursSpent != null ? !totalHoursSpent.equals(that.totalHoursSpent) : that.totalHoursSpent != null)
            return false;
        if (tasksDevelopmentData != null ? !tasksDevelopmentData.equals(that.tasksDevelopmentData) : that.tasksDevelopmentData != null)
            return false;

        return requestsForDevelopers != null ? requestsForDevelopers.equals(that.requestsForDevelopers)
                : that.requestsForDevelopers == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (operationId != null ? operationId.hashCode() : 0);
        result = 31 * result + (taskStatus != null ? taskStatus.hashCode() : 0);
        result = 31 * result + (totalHoursSpent != null ? totalHoursSpent.hashCode() : 0);
        result = 31 * result + (tasksDevelopmentData != null ? tasksDevelopmentData.hashCode() : 0);
        result = 31 * result + (requestsForDevelopers != null ? requestsForDevelopers.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProjectTask{");
        sb.append("projectId=").append(projectId);
        sb.append(", operationId=").append(operationId);
        sb.append(", taskStatus=").append(taskStatus);
        sb.append(", totalHoursSpent=").append(totalHoursSpent);
        sb.append(", taskDevelopers=").append(tasksDevelopmentData);
        sb.append(", requestsForDevelopers=").append(requestsForDevelopers);
        sb.append('}');
        return sb.toString();
    }
}
