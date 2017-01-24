package ua.devteam.entity.projects;


import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.ProjectTask;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Project is creating when manager accepts customers technical task. Contains technical task id that project is
 * formed from, and when status of project is updated - status of technical task must be updated as well.
 * {@link TechnicalTask}
 */
public class Project extends AbstractTechnicalTask implements Serializable{
    /* General date format */
    private final static DateFormat PROJECT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    /* MANAGER id that assigned to this project */
    private Long managerId;
    /* Technical task id that project is formed from */
    private Long technicalTaskId;
    /* Date when project is started. Usually its time of creating project object  */
    private Date startDate;
    /* Date when project is complete, declined or cancelled */
    private Date endDate;
    /* Total cost of project. May be null if project was declined. */
    private BigDecimal totalProjectCost;
    /* Tasks that are bound to project */
    private List<ProjectTask> tasks;

    public Project() {
    }

    public Project(Long managerId, TechnicalTask technicalTask) {
        this(technicalTask.getName(), technicalTask.getDescription(), technicalTask.getCustomerId(), managerId, null,
                technicalTask.getId(), null, new Date(), null, Status.NEW);
    }

    public Project(String name, String description, Long customerId, Long managerId, String managerCommentary,
                   Long technicalTaskId, BigDecimal totalProjectCost, Date startDate, Date endDate, Status status) {
        this(null, name, description, customerId, managerId, managerCommentary, technicalTaskId, totalProjectCost,
                startDate, endDate, status);
    }

    public Project(Long id, String name, String description, Long customerId, Long managerId, String managerCommentary,
                   Long technicalTaskId, BigDecimal totalProjectCost, Date startDate, Date endDate, Status status) {
        this(id, name, description, customerId, managerId, managerCommentary, technicalTaskId, totalProjectCost,
                startDate, endDate, status, null);
    }

    public Project(Long id, String name, String description, Long customerId, Long managerId, String managerCommentary,
                   Long technicalTaskId, BigDecimal totalProjectCost, Date startDate, Date endDate, Status status,
                   List<ProjectTask> tasks) {
        super(id, name, description, customerId, status, managerCommentary);
        this.managerId = managerId;
        this.technicalTaskId = technicalTaskId;
        this.totalProjectCost = totalProjectCost;
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

    public BigDecimal getTotalProjectCost() {
        return totalProjectCost;
    }

    public void setTotalProjectCost(BigDecimal totalProjectCost) {
        this.totalProjectCost = totalProjectCost;
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
        if (totalProjectCost != null ? !totalProjectCost.equals(project.totalProjectCost) : project.totalProjectCost != null)
            return false;

        return tasks != null ? tasks.equals(project.tasks) : project.tasks == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (technicalTaskId != null ? technicalTaskId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (totalProjectCost != null ? totalProjectCost.hashCode() : 0);
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
        sb.append(", totalProjectCost").append(totalProjectCost);
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
