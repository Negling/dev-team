package ua.devteam.entity.projects;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.Operation;

import java.io.Serializable;
import java.util.List;

/**
 * Simple realization of high-tier task. Object of this class is creating when customer submits request to managers.
 * If Manager confirms technical task - from it creates relied object of project. Or task may be just declined.
 * Technical task status must depend on project status, if there was created one.
 * {@link Project}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class TechnicalTask extends AbstractTechnicalTask implements Serializable {
    /* Operations that bound to this technical task */
    private List<Operation> operations;

    public TechnicalTask(String name, String description, Long customerId, String managerCommentary, Status status) {
        this(null, name, description, customerId, managerCommentary, status, null);
    }

    public TechnicalTask(Long id, String name, String description, Long customerId, String managerCommentary, Status status) {
        this(id, name, description, customerId, managerCommentary, status, null);
    }

    public TechnicalTask(Long id, String name, String description, Long customerId, String managerCommentary, Status status,
                         List<Operation> operations) {
        super(id, name, description, customerId, status, managerCommentary);
        this.operations = operations;
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);
        operations.forEach(operation -> operation.setTechnicalTaskId(id));
    }
}
