package ua.devteam.entity.projects;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractTask;
import ua.devteam.entity.enums.Status;

import java.io.Serializable;

/**
 * Task of high-tier. Contains id of submitted it customer, current status and commentary of manager, in case of declining.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
abstract class AbstractTechnicalTask extends AbstractTask implements Serializable {
    /* Id of customer that submitted this task */
    private Long customerId;
    /* Current status of task */
    private Status status;
    /* Commentary of manager. In most cases uses to describe reason of decline. Can be null or empty as well */
    private String managerCommentary;

    AbstractTechnicalTask(Long id, String name, String description, Long customerId, Status status, String managerCommentary) {
        super(id, name, description);
        this.customerId = customerId;
        this.status = status;
        this.managerCommentary = managerCommentary;
    }
}
