package ua.devteam.entity.projects;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
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
}
