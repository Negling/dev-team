package ua.devteam.entity.projects;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractBuilder;
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

    private TechnicalTask(String name, String description, Long customerId) {
        super(null, name, description, customerId, Status.NEW, null);
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);
        operations.forEach(operation -> operation.setTechnicalTaskId(id));
    }

    public static class Builder extends AbstractBuilder<TechnicalTask> {
        public Builder(String name, String description, Long customerId) {
            super(new TechnicalTask(name, description, customerId));
        }

        public Builder setId(long id) {
            instance.setId(id);
            return this;
        }

        public Builder setStatus(Status status) {
            instance.setStatus(status);
            return this;
        }

        public Builder setManagerCommentary(String managerCommentary) {
            instance.setManagerCommentary(managerCommentary);
            return this;
        }

        public Builder setOperations(List<Operation> operations) {
            instance.setOperations(operations);
            return this;
        }
    }
}
