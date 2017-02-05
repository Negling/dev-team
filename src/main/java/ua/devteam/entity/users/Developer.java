package ua.devteam.entity.users;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractBuilder;
import ua.devteam.entity.Operation;
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
            return perform(() -> getConstruction().setCurrentTaskId(currentTaskId));
        }

        public Builder setHireCost(BigDecimal hireCost) {
            return perform(() -> getConstruction().setHireCost(hireCost));
        }

        public Builder setRank(DeveloperRank rank) {
            return perform(() -> getConstruction().setRank(rank));
        }

        public Builder setSpecialization(DeveloperSpecialization specialization) {
            return perform(() -> getConstruction().setSpecialization(specialization));
        }

        public Builder setStatus(DeveloperStatus status) {
            return perform(() -> getConstruction().setStatus(status));
        }

        public Builder setId(Long id) {
            return perform(() -> getConstruction().setId(id));
        }

        public Builder setFirstName(String firstName) {
            return perform(() -> getConstruction().setFirstName(firstName));
        }

        public Builder setLastName(String lastName) {
            return perform(() -> getConstruction().setLastName(lastName));
        }

        public Builder setEmail(String email) {
            return perform(() -> getConstruction().setEmail(email));
        }

        public Builder setPhoneNumber(String phoneNumber) {
            return perform(() -> getConstruction().setPhoneNumber(phoneNumber));
        }

        public Builder setPassword(String password) {
            return perform(() -> getConstruction().setPassword(password));
        }

        public Builder setRole(Role role) {
            return perform(() -> getConstruction().setRole(role));
        }

        @Override
        protected Builder perform(Operation operation) {
            operation.perform();
            return this;
        }
    }
}
