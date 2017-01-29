package ua.devteam.entity.tasks;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
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

    public TaskDevelopmentData(Long projectTaskId, Long developerId, DeveloperSpecialization specialization, DeveloperRank rank) {
        this(projectTaskId, developerId, specialization, rank, null, null);
    }

    public TaskDevelopmentData(Long projectTaskId, Long developerId, DeveloperSpecialization specialization,
                               DeveloperRank rank, Integer hoursSpent, Status status) {
        this(projectTaskId, null, null, developerId, null, null, specialization, rank, null, hoursSpent, status);
    }
}
