package ua.devteam.entity.users;


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
public class Developer extends User implements Serializable {

    /* Id of current project task that developer works on. Can be null if developer is not assigned to task  */
    private Long currentTaskId;
    /* Developer salary-hire cost. Unit if measure is USD. */
    private BigDecimal hireCost;
    /* Specialization. This defines type of works that developer is capable for */
    private DeveloperSpecialization specialization;
    /* Rank of developer */
    private DeveloperRank rank;
    /* Current developer status. Defines is developer available to hire, or he is already assigned to task */
    private DeveloperStatus status;

    public Developer() {
    }

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

    public Long getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(Long currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public BigDecimal getHireCost() {
        return hireCost;
    }

    public void setHireCost(BigDecimal hireCost) {
        this.hireCost = hireCost;
    }

    public DeveloperSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(DeveloperSpecialization specialization) {
        this.specialization = specialization;
    }

    public DeveloperRank getRank() {
        return rank;
    }

    public void setRank(DeveloperRank rank) {
        this.rank = rank;
    }

    public DeveloperStatus getStatus() {
        return status;
    }

    public void setStatus(DeveloperStatus status) {
        this.status = status;
    }

    @Override
    public void setRole(Role role) {
        super.setRole(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Developer developer = (Developer) o;

        if (currentTaskId != null ? !currentTaskId.equals(developer.currentTaskId) : developer.currentTaskId != null)
            return false;
        if (hireCost != null ? !hireCost.equals(developer.hireCost) : developer.hireCost != null) return false;
        if (specialization != developer.specialization) return false;
        if (rank != developer.rank) return false;

        return status == developer.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (currentTaskId != null ? currentTaskId.hashCode() : 0);
        result = 31 * result + (hireCost != null ? hireCost.hashCode() : 0);
        result = 31 * result + (specialization != null ? specialization.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "currentTaskId=" + currentTaskId +
                ", hireCost=" + hireCost +
                ", specialization=" + specialization +
                ", rank=" + rank +
                ", status=" + status +
                "} " + super.toString();
    }
}
