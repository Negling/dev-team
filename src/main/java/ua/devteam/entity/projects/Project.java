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
    private Long managerId;
    private Long technicalTaskId;
    private Date startDate;
    private Date endDate;
    private List<ProjectTask> tasks;

    public Project() {
    }

    public Project(Long managerId, TechnicalTask technicalTask) {
        this(technicalTask.getName(), technicalTask.getDescription(), technicalTask.getCustomerId(), managerId, null,
                technicalTask.getId(), new Date(), null, Status.New);
    }

    public Project(String name, String description, Long customerId, Long managerId, String managerCommentary,
                   Long technicalTaskId, Date startDate, Date endDate, Status status) {
        this(null, name, description, customerId, managerId, managerCommentary, technicalTaskId, startDate, endDate, status);
    }

    public Project(Long id, String name, String description, Long customerId, Long managerId, String managerCommentary,
                   Long technicalTaskId, Date startDate, Date endDate, Status status) {
        this(id, name, description, customerId, managerId, managerCommentary, technicalTaskId, startDate, endDate, status, null);
    }

    public Project(Long id, String name, String description, Long customerId, Long managerId, String managerCommentary,
                   Long technicalTaskId, Date startDate, Date endDate, Status status, List<ProjectTask> tasks) {

        super(id, name, description, customerId, status, managerCommentary);
        this.managerId = managerId;
        this.technicalTaskId = technicalTaskId;
        this.startDate = formatDate(startDate);
        this.endDate = formatDate(endDate);
        this.tasks = tasks;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
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

    public List<ProjectTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ProjectTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);

        tasks.forEach(projectTask -> projectTask.setProjectId(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        if (!super.equals(o)) return false;

        Project project = (Project) o;

        if (managerId != null ? !managerId.equals(project.managerId) : project.managerId != null) return false;
        if (technicalTaskId != null ? !technicalTaskId.equals(project.technicalTaskId) : project.technicalTaskId != null)
            return false;
        if (startDate != null ? !startDate.equals(project.startDate) : project.startDate != null) return false;
        if (endDate != null ? !endDate.equals(project.endDate) : project.endDate != null) return false;

        return tasks != null ? tasks.equals(project.tasks) : project.tasks == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (technicalTaskId != null ? technicalTaskId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Project{");
        sb.append("id=").append(getId());
        sb.append(", customerId=").append(getCustomerId());
        sb.append(", managerId=").append(managerId);
        sb.append(", managerCommentary=").append(getManagerCommentary());
        sb.append(", name=").append(getName());
        sb.append(", description=").append(getDescription());
        sb.append(", technicalTaskId=").append(technicalTaskId);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", status=").append(getStatus().toString());
        sb.append(", tasks=").append(tasks);
        sb.append('}');
        return sb.toString();
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
