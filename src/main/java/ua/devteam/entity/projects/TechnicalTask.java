package ua.devteam.entity.projects;


import ua.devteam.entity.tasks.Operation;

import java.util.List;

public class TechnicalTask extends AbstractTechnicalTask {
    private List<Operation> operations;

    public TechnicalTask() {
    }

    public TechnicalTask(Long customerId, Long managerId, String name, String description) {
        this(null, customerId, managerId, name, description);
    }

    public TechnicalTask(Long id, Long customerId, Long managerId, String name, String description) {
        this(id, customerId, managerId, name, description, null);
    }

    public TechnicalTask(Long id, Long customerId, Long managerId, String name, String description, List<Operation> operations) {
        super(id, name, description, managerId, customerId);
        this.operations = operations;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
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
        sb.append("name=").append(this.getName());
        sb.append(", description=").append(this.getDescription());
        sb.append(", customerId=").append(this.getCustomerId());
        sb.append(", managerId=").append(this.getManagerId());
        sb.append(", operations=").append(operations);
        sb.append('}');
        return sb.toString();
    }
}
