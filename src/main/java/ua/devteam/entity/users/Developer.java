package ua.devteam.entity.users;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractBuilder;
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

    public static class Builder extends AbstractBuilder<Developer>{
        public Builder() {
            super(new Developer());
        }

        public Builder setCurrentTaskId(Long currentTaskId) {
            instance.setCurrentTaskId(currentTaskId);
            return this;
        }

        public Builder setHireCost(BigDecimal hireCost) {
            instance.setHireCost(hireCost);
            return this;
        }

        public Builder setRank(DeveloperRank rank) {
            instance.setRank(rank);
            return this;
        }

        public Builder setSpecialization(DeveloperSpecialization specialization) {
            instance.setSpecialization(specialization);
            return this;
        }

        public Builder setStatus(DeveloperStatus status) {
            instance.setStatus(status);
            return this;
        }

        public Builder setId(Long id) {
            instance.setId(id);
            return this;
        }

        public Builder setFirstName(String firstName) {
            instance.setFirstName(firstName);
            return this;
        }

        public Builder setLastName(String lastName) {
            instance.setLastName(lastName);
            return this;
        }

        public Builder setEmail(String email) {
            instance.setEmail(email);
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            instance.setPhoneNumber(phoneNumber);
            return this;
        }

        public Builder setPassword(String password) {
            instance.setPassword(password);
            return this;
        }

        public Builder setRole(Role role) {
            instance.setRole(role);
            return this;
        }
    }
}
