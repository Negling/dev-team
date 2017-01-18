package ua.devteam.entity.tasks;


import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.enums.Status;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Contains information about assigned to task developer, current development status of this developer,
 * and hours that developer spent to it.
 * {@link ProjectTask}
 */
public class TaskDevelopmentData implements Serializable {

    /* Id of task that this development data is bind to */
    private Long projectTaskId;
    /* Name of task that this development data is bind to */
    private String taskName;
    /* Description of task that this development data is bind to */
    private String taskDescription;
    /* Id of developer that assigned on task */
    private Long developerId;
    /* First name of developer that assigned on task */
    private String developerFirstName;
    /* Last name of developer that assigned on task */
    private String developerLastName;
    /* Specialization of developer that assigned on task */
    private DeveloperSpecialization specialization;
    /* Rank of developer that assigned on task */
    private DeveloperRank rank;
    /* Hire cost of developer that assigned on task */
    private BigDecimal hireCost;
    /* Hours that developer has(or will) spent on this task development */
    private Integer hoursSpent;
    /* Current development status of concrete developer assigned to task */
    private Status status;

    public TaskDevelopmentData() {
    }

    public TaskDevelopmentData(Long projectTaskId, Long developerId, DeveloperSpecialization specialization, DeveloperRank rank) {
        this.projectTaskId = projectTaskId;
        this.developerId = developerId;
        this.specialization = specialization;
        this.rank = rank;
    }

    public TaskDevelopmentData(Long projectTaskId, String taskName, String taskDescription, Long developerId,
                               String developerFirstName, String developerLastName, DeveloperSpecialization specialization,
                               DeveloperRank rank, BigDecimal hireCost, Integer hoursSpent, Status status) {
        this.projectTaskId = projectTaskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.developerId = developerId;
        this.developerFirstName = developerFirstName;
        this.developerLastName = developerLastName;
        this.specialization = specialization;
        this.rank = rank;
        this.hireCost = hireCost;
        this.hoursSpent = hoursSpent;
        this.status = status;
    }

    public Long getProjectTaskId() {
        return projectTaskId;
    }

    public void setProjectTaskId(Long projectTaskId) {
        this.projectTaskId = projectTaskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }

    public String getDeveloperFirstName() {
        return developerFirstName;
    }

    public void setDeveloperFirstName(String developerFirstName) {
        this.developerFirstName = developerFirstName;
    }

    public String getDeveloperLastName() {
        return developerLastName;
    }

    public void setDeveloperLastName(String developerLastName) {
        this.developerLastName = developerLastName;
    }

    public DeveloperSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(DeveloperSpecialization specialization) {
        this.specialization = specialization;
    }

    public DeveloperRank getRank() {
        return rank;
    }

    public void setRank(DeveloperRank rank) {
        this.rank = rank;
    }

    public BigDecimal getHireCost() {
        return hireCost;
    }

    public void setHireCost(BigDecimal hireCost) {
        this.hireCost = hireCost;
    }

    public Integer getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Integer hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskDevelopmentData)) return false;

        TaskDevelopmentData that = (TaskDevelopmentData) o;

        if (projectTaskId != null ? !projectTaskId.equals(that.projectTaskId) : that.projectTaskId != null)
            return false;
        if (taskName != null ? !taskName.equals(that.taskName) : that.taskName != null) return false;
        if (taskDescription != null ? !taskDescription.equals(that.taskDescription) : that.taskDescription != null)
            return false;
        if (developerId != null ? !developerId.equals(that.developerId) : that.developerId != null) return false;
        if (developerFirstName != null ? !developerFirstName.equals(that.developerFirstName) : that.developerFirstName != null)
            return false;
        if (developerLastName != null ? !developerLastName.equals(that.developerLastName) : that.developerLastName != null)
            return false;
        if (specialization != that.specialization) return false;
        if (rank != that.rank) return false;
        if (hireCost != null ? !hireCost.equals(that.hireCost) : that.hireCost != null) return false;
        if (hoursSpent != null ? !hoursSpent.equals(that.hoursSpent) : that.hoursSpent != null) return false;

        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = projectTaskId != null ? projectTaskId.hashCode() : 0;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (taskDescription != null ? taskDescription.hashCode() : 0);
        result = 31 * result + (developerId != null ? developerId.hashCode() : 0);
        result = 31 * result + (developerFirstName != null ? developerFirstName.hashCode() : 0);
        result = 31 * result + (developerLastName != null ? developerLastName.hashCode() : 0);
        result = 31 * result + (specialization != null ? specialization.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (hireCost != null ? hireCost.hashCode() : 0);
        result = 31 * result + (hoursSpent != null ? hoursSpent.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskDeveloper{");
        sb.append("projectTaskId=").append(projectTaskId);
        sb.append(", taskName=").append(taskName);
        sb.append(", taskDescription=").append(taskDescription);
        sb.append(", developerId=").append(developerId);
        sb.append(", developerFirstName='").append(developerFirstName).append('\'');
        sb.append(", developerLastName='").append(developerLastName).append('\'');
        sb.append(", specialization=").append(specialization);
        sb.append(", rank=").append(rank);
        sb.append(", hireCost=").append(hireCost);
        sb.append(", hoursSpent=").append(hoursSpent);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
