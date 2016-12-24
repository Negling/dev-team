package ua.devteam.entity.tasks;


import ua.devteam.entity.AbstractTask;
import ua.devteam.entity.enums.Status;

import java.util.List;

public class ProjectTask extends AbstractTask {
    private Long projectId;
    private Status taskStatus;
    private Integer totalHoursSpent;
    private List<TaskDeveloper> taskDevelopers;

    public ProjectTask() {
    }

    public ProjectTask(Long projectId, String name, String description, Status taskStatus, Integer totalHoursSpent) {
        this(null, projectId, name, description, taskStatus, totalHoursSpent);
    }

    public ProjectTask(Long id, Long projectId, String name, String description, Status taskStatus, Integer totalHoursSpent) {
        this(id, projectId, name, description, taskStatus, totalHoursSpent, null);
    }

    public ProjectTask(Long id, Long projectId, String name, String description, Status taskStatus, Integer totalHoursSpent,
                       List<TaskDeveloper> taskDevelopers) {
        super(id, name, description);
        this.projectId = projectId;
        this.taskStatus = taskStatus;
        this.totalHoursSpent = totalHoursSpent;
        this.taskDevelopers = taskDevelopers;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public List<TaskDeveloper> getTaskDevelopers() {
        return taskDevelopers;
    }

    public void setTaskDevelopers(List<TaskDeveloper> taskDevelopers) {
        this.taskDevelopers = taskDevelopers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectTask)) return false;
        if (!super.equals(o)) return false;

        ProjectTask that = (ProjectTask) o;

        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (taskStatus != that.taskStatus) return false;
        if (totalHoursSpent != null ? !totalHoursSpent.equals(that.totalHoursSpent) : that.totalHoursSpent != null)
            return false;

        return taskDevelopers != null ? taskDevelopers.equals(that.taskDevelopers) : that.taskDevelopers == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (taskStatus != null ? taskStatus.hashCode() : 0);
        result = 31 * result + (totalHoursSpent != null ? totalHoursSpent.hashCode() : 0);
        result = 31 * result + (taskDevelopers != null ? taskDevelopers.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "ProjectTask{" +
                "projectId=" + projectId +
                ", taskStatus=" + taskStatus +
                ", totalHoursSpent=" + totalHoursSpent +
                ", taskDevelopers=" + taskDevelopers +
                "} " + super.toString();
    }
}
