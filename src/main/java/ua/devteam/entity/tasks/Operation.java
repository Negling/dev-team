package ua.devteam.entity.tasks;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractBuilder;
import ua.devteam.entity.AbstractTask;

import java.io.Serializable;
import java.util.List;

/**
 * Low-tier realization of abstract task class. Used as nested technical task object to describe operations that
 * needs to be done in technical task.
 * {@link ua.devteam.entity.projects.TechnicalTask}
 * {@link RequestForDevelopers}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class Operation extends AbstractTask implements Serializable {
    /* Id of technical task that operation is bound to */
    private Long technicalTaskId;
    /* Developers that customer is requested to perform this operation */
    private List<RequestForDevelopers> requestsForDevelopers;

    private Operation(Long technicalTaskId, String name, String description) {
        super(null, name, description);
        this.technicalTaskId = technicalTaskId;
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);
        requestsForDevelopers.forEach(requestForDevelopers -> requestForDevelopers.setOperationId(id));
    }

    public static class Builder extends AbstractBuilder<Operation> {
        public Builder(Long technicalTaskId, String name, String description) {
            super(new Operation(technicalTaskId, name, description));
        }

        public Builder setRequestsForDevelopers(List<RequestForDevelopers> requestsForDevelopers) {
            instance.setRequestsForDevelopers(requestsForDevelopers);
            return this;
        }

        public Builder setId(Long id) {
            instance.setId(id);
            return this;
        }
    }
}
