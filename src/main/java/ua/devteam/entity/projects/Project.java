package ua.devteam.entity.projects;


import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.ProjectTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Project extends AbstractTechnicalTask {
    private final static DateFormat PROJECT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private Long technicalTaskId;
    private Date startDate;
    private Date endDate;
    private Status status;
    private List<ProjectTask> tasks;

    public Project() {
    }

    public Project(Long managerId, Long customerId, Long technicalTaskId, String name, String description, Date startDate,
                   Date endDate, Status status) {
        this(null, managerId, customerId, technicalTaskId,  name, description, startDate, endDate, status);
    }

    public Project(Long id, Long managerId, Long customerId, Long technicalTaskId, String name, String description, Date startDate,
                   Date endDate, Status status) {
        this(id, managerId, customerId, technicalTaskId,  name, description, startDate, endDate, status, null);
    }

    public Project(Long id, Long managerId, Long customerId, Long technicalTaskId, String name, String description,
                   Date startDate, Date endDate, Status status, List<ProjectTask> tasks) {

        super(id, name, description, managerId, customerId);
        this.technicalTaskId = technicalTaskId;
        this.startDate = formatDate(startDate);
        this.endDate = formatDate(endDate);
        this.status = status;
        this.tasks = tasks;
    }

    public Long getTechnicalTaskId() {
        return technicalTaskId;
    }

    public void setTechnicalTaskId(Long technicalTaskId) {
        this.technicalTaskId = technicalTaskId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = formatDate(startDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = formatDate(endDate);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ProjectTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ProjectTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        if (!super.equals(o)) return false;

        Project project = (Project) o;

        if (technicalTaskId != null ? !technicalTaskId.equals(project.technicalTaskId) : project.technicalTaskId != null)
            return false;
        if (startDate != null ? !startDate.equals(project.startDate) : project.startDate != null) return false;
        if (endDate != null ? !endDate.equals(project.endDate) : project.endDate != null) return false;
        if (status != project.status) return false;

        return tasks != null ? tasks.equals(project.tasks) : project.tasks == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (technicalTaskId != null ? technicalTaskId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "technicalTaskId=" + technicalTaskId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", tasks=" + tasks +
                "} " + super.toString();
    }

    private Date formatDate(Date date) {
        if (date != null) {
            try {
                return PROJECT_DATE_FORMAT.parse(PROJECT_DATE_FORMAT.format(date));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Illegal date params!", e);
            }
        } else {
            return null;
        }
    }
}
