package ua.devteam.entity.tasks;


import lombok.Data;
import lombok.NoArgsConstructor;
import ua.devteam.entity.AbstractBuilder;
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
            instance.setDeveloperFirstName(developerFirstName);
            return this;
        }

        public Builder setDeveloperLastName(String developerLastName) {
            instance.setDeveloperLastName(developerLastName);
            return this;
        }

        public Builder setHireCost(BigDecimal hireCost) {
            instance.setHireCost(hireCost);
            return this;
        }

        public Builder setHoursSpent(Integer hoursSpent) {
            instance.setHoursSpent(hoursSpent);
            return this;
        }

        public Builder setStatus(Status status) {
            instance.setStatus(status);
            return this;
        }

        public Builder setTaskDescription(String taskDescription) {
            instance.setTaskDescription(taskDescription);
            return this;
        }

        public Builder setTaskName(String taskName) {
            instance.setTaskName(taskName);
            return this;
        }
    }
}
