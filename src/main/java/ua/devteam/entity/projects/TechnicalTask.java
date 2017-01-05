package ua.devteam.entity.projects;


import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.Operation;

import java.util.List;

public class TechnicalTask extends AbstractTechnicalTask {
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
        sb.append("id=").append(this.getId());
        sb.append(", customerId=").append(this.getCustomerId());
        sb.append(", managerCommentary=").append(getManagerCommentary());
        sb.append(", name=").append(this.getName());
        sb.append(", description=").append(this.getDescription());
        sb.append(", status=").append(getStatus().toString());
        sb.append(", operations=").append(operations);
        sb.append('}');
        return sb.toString();
    }
}
