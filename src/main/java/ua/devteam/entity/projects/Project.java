package ua.devteam.entity.projects;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractBuilder;
import ua.devteam.entity.Operation;
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

    private Project(String name, String description, Long customerId, Long managerId) {
        super(null, name, description, customerId, Status.NEW, null);
        this.managerId = managerId;
        this.startDate = formatDate(new Date());
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
        try {
            return PROJECT_DATE_FORMAT.parse(PROJECT_DATE_FORMAT.format(date));
        } catch (ParseException | NullPointerException e) {
            throw new IllegalArgumentException("Illegal date params!", e);
        }
    }

    public static class Builder extends AbstractBuilder<Project> {
        public Builder(String name, String description, Long customerId, Long managerId) {
            super(new Project(name, description, customerId, managerId));
        }

        public Builder setId(Long id) {
            return perform(() -> getConstruction().setId(id));
        }

        public Builder setTasks(List<ProjectTask> tasks) {
            return perform(() -> getConstruction().setTasks(tasks));
        }

        public Builder setTechnicalTaskId(Long technicalTaskId) {
            return perform(() -> getConstruction().setTechnicalTaskId(technicalTaskId));
        }

        public Builder setTotalProjectCost(BigDecimal totalProjectCost) {
            return perform(() -> getConstruction().setTotalProjectCost(totalProjectCost));
        }

        public Builder setStartDate(Date startDate) {
            return perform(() -> getConstruction().setStartDate(startDate));
        }

        public Builder setEndDate(Date endDate) {
            return perform(() -> getConstruction().setEndDate(endDate));
        }

        public Builder setStatus(Status status) {
            return perform(() -> getConstruction().setStatus(status));
        }

        public Builder setManagerCommentary(String managerCommentary) {
            return perform(() -> getConstruction().setManagerCommentary(managerCommentary));
        }

        @Override
        protected Builder perform(Operation operation) {
            operation.perform();
            return this;
        }
    }
}
