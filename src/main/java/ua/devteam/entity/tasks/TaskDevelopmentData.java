package ua.devteam.entity.tasks;


import lombok.Data;
import lombok.NoArgsConstructor;
import ua.devteam.entity.AbstractBuilder;
import ua.devteam.entity.Operation;
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
@Data
@NoArgsConstructor
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

    private TaskDevelopmentData(Long projectTaskId, Long developerId, DeveloperSpecialization specialization, DeveloperRank rank) {
        this.projectTaskId = projectTaskId;
        this.developerId = developerId;
        this.specialization = specialization;
        this.rank = rank;
    }

    public static class Builder extends AbstractBuilder<TaskDevelopmentData> {
        public Builder(Long projectTaskId, Long developerId, DeveloperSpecialization specialization, DeveloperRank rank) {
            super(new TaskDevelopmentData(projectTaskId, developerId, specialization, rank));
        }

        public Builder setDeveloperFirstName(String developerFirstName) {
            return perform(() -> getConstruction().setDeveloperFirstName(developerFirstName));
        }

        public Builder setDeveloperLastName(String developerLastName) {
            return perform(() -> getConstruction().setDeveloperLastName(developerLastName));
        }

        public Builder setHireCost(BigDecimal hireCost) {
            return perform(() -> getConstruction().setHireCost(hireCost));
        }

        public Builder setHoursSpent(Integer hoursSpent) {
            return perform(() -> getConstruction().setHoursSpent(hoursSpent));
        }

        public Builder setStatus(Status status) {
            return perform(() -> getConstruction().setStatus(status));
        }

        public Builder setTaskDescription(String taskDescription) {
            return perform(() -> getConstruction().setTaskDescription(taskDescription));
        }

        public Builder setTaskName(String taskName) {
            return perform(() -> getConstruction().setTaskName(taskName));
        }

        @Override
        protected Builder perform(Operation operation) {
            operation.perform();
            return this;
        }
    }
}
