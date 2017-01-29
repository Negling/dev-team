package ua.devteam.entity.users;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.enums.DeveloperStatus;
import ua.devteam.entity.enums.Role;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * User subclass. Contains information about developer specialization and class, his current task, status and hire cost.
 * {@link User}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class Developer extends User implements Serializable {

    /* Id of current project task that developer works on. Can be null if developer is not assigned to task  */
    private Long currentTaskId;
    /* DEVELOPER salary-hire cost. Unit if measure is USD. */
    private BigDecimal hireCost;
    /* Specialization. This defines type of works that developer is capable for */
    private DeveloperSpecialization specialization;
    /* Rank of developer */
    private DeveloperRank rank;
    /* Current developer status. Defines is developer available to hire, or he is already assigned to task */
    private DeveloperStatus status;


    public Developer(String firstName, String lastName, String email, String phoneNumber, String password,
                     Role role, Long currentTaskId, BigDecimal hireCost, DeveloperSpecialization specialization,
                     DeveloperRank rank, DeveloperStatus status) {

        this(null, firstName, lastName, email, phoneNumber, password, role, currentTaskId, hireCost, specialization,
                rank, status);
    }

    public Developer(Long id, String firstName, String lastName, String email, String phoneNumber, String password,
                     Role role, Long currentTaskId, BigDecimal hireCost, DeveloperSpecialization specialization,
                     DeveloperRank rank, DeveloperStatus status) {

        super(id, firstName, lastName, email, phoneNumber, password, role);
        this.currentTaskId = currentTaskId;
        this.hireCost = hireCost;
        this.specialization = specialization;
        this.rank = rank;
        this.status = status;
    }
}
