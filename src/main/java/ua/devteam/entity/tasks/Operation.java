package ua.devteam.entity.tasks;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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

    public Operation(Long technicalTaskId, String name, String description) {
        this(null, technicalTaskId, name, description);
    }

    public Operation(Long id, Long technicalTaskId, String name, String description) {
        this(id, technicalTaskId, name, description, null);
    }

    public Operation(Long id, Long technicalTaskId, String name, String description, List<RequestForDevelopers> requestsForDevelopers) {
        super(id, name, description);
        this.technicalTaskId = technicalTaskId;
        this.requestsForDevelopers = requestsForDevelopers;
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);
        requestsForDevelopers.forEach(requestForDevelopers -> requestForDevelopers.setOperationId(id));
    }
}
