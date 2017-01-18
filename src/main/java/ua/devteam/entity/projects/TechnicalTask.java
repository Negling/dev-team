package ua.devteam.entity.projects;


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
public class TechnicalTask extends AbstractTechnicalTask implements Serializable {
    /* Operations that bound to this technical task */
    private List<Operation> operations;

    public TechnicalTask() {
    }

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

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);

        operations.forEach(operation -> operation.setTechnicalTaskId(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TechnicalTask)) return false;
        if (!super.equals(o)) return false;

        TechnicalTask that = (TechnicalTask) o;

        return operations != null ? operations.equals(that.operations) : that.operations == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (operations != null ? operations.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TechnicalTask{");
        sb.append("id=").append(getId());
        sb.append(", customerId=").append(getCustomerId());
        sb.append(", managerCommentary=").append(getManagerCommentary());
        sb.append(", name=").append(getName());
        sb.append(", description=").append(getDescription());
        sb.append(", status=").append(getStatus() == null ? null : getStatus().toString());
        sb.append(", operations=").append(operations);
        sb.append('}');
        return sb.toString();
    }
}
