package ua.devteam.entity.projects;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class Project extends AbstractTechnicalTask implements Serializable {
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

    public void setStartDate(Date startDate) {
        this.startDate = formatDate(startDate);
    }


    public void setEndDate(Date endDate) {
        this.endDate = formatDate(endDate);
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);
        tasks.forEach(projectTask -> projectTask.setProjectId(id));
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
