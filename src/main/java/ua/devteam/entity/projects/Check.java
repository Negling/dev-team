package ua.devteam.entity.projects;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.devteam.entity.AbstractBuilder;
import ua.devteam.entity.enums.CheckStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Represents check that sent to customer, when manager completes forming the Project. After check is created it has
 * "PENDING" status, customer may accept check, and this decision runs related project, or may decline payment.
 * Declining check cancels project as well.
 * {@link Project}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Check implements Serializable {
    /* Project id this check is bind to */
    private Long projectId;
    /* Customer id this check is bind to */
    private Long customerId;
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

    public BigDecimal getTotalProjectCost() {
        return developersCost.add(taxes).add(servicesCost);
    }

    public static class Builder extends AbstractBuilder<Check> {
        public Builder(BigDecimal developersCost, BigDecimal servicesCost, BigDecimal taxes, CheckStatus status) {
            super(new Check(null, null, null, developersCost, servicesCost, taxes, status));
        }

        public Builder setProjectId(Long projectId){
            instance.setProjectId(projectId);
            return this;
        }

        public Builder setCustomerId(Long customerId){
            instance.setCustomerId(customerId);
            return this;
        }

        public Builder setProjectName(String projectName){
            instance.setProjectName(projectName);
            return this;
        }
    }
}
