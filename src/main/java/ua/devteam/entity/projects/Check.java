package ua.devteam.entity.projects;


import ua.devteam.entity.enums.CheckStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Represents check that sent to customer, when manager completes forming the Project. After check is created it has
 * "PENDING" status, customer may accept check, and this decision runs related project, or may decline payment.
 * Declining check cancels project as well.
 * {@link Project}
 */
public class Check implements Serializable {
    /* Project id this check is bind to */
    private Long projectId;
    /* Project name this check is bind to */
    private String projectName;
    /* Total cost to hire all assigned to this project developers */
    private BigDecimal developersCost;
    /* Total cost to provide all services project needs */
    private BigDecimal servicesCost;
    /* Taxes as 20% value of services and developers hire cost */
    private BigDecimal taxes;
    /* Check current status  */
    private CheckStatus status;

    public Check() {
    }

    public Check(Long projectId, String projectName, BigDecimal developersCost, BigDecimal servicesCost, BigDecimal taxes,
                 CheckStatus status) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.developersCost = developersCost;
        this.servicesCost = servicesCost;
        this.taxes = taxes;
        this.status = status;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getDevelopersCost() {
        return developersCost;
    }

    public void setDevelopersCost(BigDecimal developersCost) {
        this.developersCost = developersCost;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getServicesCost() {
        return servicesCost;
    }

    public void setServicesCost(BigDecimal servicesCost) {
        this.servicesCost = servicesCost;
    }

    public CheckStatus getStatus() {
        return status;
    }

    public void setStatus(CheckStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalProjectCost() {
        return developersCost.add(taxes).add(servicesCost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Check)) return false;

        Check check = (Check) o;

        if (projectId != null ? !projectId.equals(check.projectId) : check.projectId != null) return false;
        if (projectName != null ? !projectName.equals(check.projectName) : check.projectName != null) return false;
        if (developersCost != null ? !developersCost.equals(check.developersCost) : check.developersCost != null)
            return false;
        if (taxes != null ? !taxes.equals(check.taxes) : check.taxes != null) return false;
        if (servicesCost != null ? !servicesCost.equals(check.servicesCost) : check.servicesCost != null) return false;

        return status == check.status;
    }

    @Override
    public int hashCode() {
        int result = projectId != null ? projectId.hashCode() : 0;
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (developersCost != null ? developersCost.hashCode() : 0);
        result = 31 * result + (taxes != null ? taxes.hashCode() : 0);
        result = 31 * result + (servicesCost != null ? servicesCost.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Check{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", developersCost=" + developersCost +
                ", servicesCost=" + servicesCost +
                ", taxes=" + taxes +
                ", status=" + status +
                ", totalProjectCost=" + getTotalProjectCost() +
                '}';
    }
}
