package ua.devteam.entity.tasks;


import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.enums.Status;

public class TaskDeveloper {
    private Long projectTaskId;
    private Long developerId;
    private String developerFirstName;
    private String developerLastName;
    private DeveloperSpecialization specialization;
    private DeveloperRank rank;
    private Integer hoursSpent;
    private Status status;

    public TaskDeveloper() {
    }

    public TaskDeveloper(Long projectTaskId, Long developerId, String developerFirstName, String developerLastName,
                         DeveloperSpecialization specialization, DeveloperRank rank, Integer hoursSpent, Status status) {
        this.projectTaskId = projectTaskId;
        this.developerId = developerId;
        this.developerFirstName = developerFirstName;
        this.developerLastName = developerLastName;
        this.specialization = specialization;
        this.rank = rank;
        this.hoursSpent = hoursSpent;
        this.status = status;
    }

    public Long getProjectTaskId() {
        return projectTaskId;
    }

    public void setProjectTaskId(Long projectTaskId) {
        this.projectTaskId = projectTaskId;
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
        if (!(o instanceof TaskDeveloper)) return false;

        TaskDeveloper that = (TaskDeveloper) o;

        if (projectTaskId != null ? !projectTaskId.equals(that.projectTaskId) : that.projectTaskId != null)
            return false;
        if (developerId != null ? !developerId.equals(that.developerId) : that.developerId != null) return false;
        if (developerFirstName != null ? !developerFirstName.equals(that.developerFirstName) : that.developerFirstName != null)
            return false;
        if (developerLastName != null ? !developerLastName.equals(that.developerLastName) : that.developerLastName != null)
            return false;
        if (specialization != that.specialization) return false;
        if (rank != that.rank) return false;
        if (hoursSpent != null ? !hoursSpent.equals(that.hoursSpent) : that.hoursSpent != null) return false;

        return status == that.status;

    }

    @Override
    public int hashCode() {
        int result = projectTaskId != null ? projectTaskId.hashCode() : 0;
        result = 31 * result + (developerId != null ? developerId.hashCode() : 0);
        result = 31 * result + (developerFirstName != null ? developerFirstName.hashCode() : 0);
        result = 31 * result + (developerLastName != null ? developerLastName.hashCode() : 0);
        result = 31 * result + (specialization != null ? specialization.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (hoursSpent != null ? hoursSpent.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "TaskDevelopment{" +
                "projectTaskId=" + projectTaskId +
                ", developerId=" + developerId +
                ", developerFirstName='" + developerFirstName + '\'' +
                ", developerLastName='" + developerLastName + '\'' +
                ", specialization=" + specialization +
                ", rank=" + rank +
                ", hoursSpent=" + hoursSpent +
                ", status=" + status +
                '}';
    }
}
