package ua.devteam.entity.projects;


import ua.devteam.entity.AbstractTask;

public abstract class AbstractTechnicalTask extends AbstractTask {
    private Long managerId;
    private Long customerId;

    AbstractTechnicalTask() {
    }

    AbstractTechnicalTask(Long id, String name, String description, Long managerId, Long customerId) {
        super(id, name, description);
        this.managerId = managerId;
        this.customerId = customerId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTechnicalTask)) return false;
        if (!super.equals(o)) return false;

        AbstractTechnicalTask that = (AbstractTechnicalTask) o;

        if (managerId != null ? !managerId.equals(that.managerId) : that.managerId != null) return false;

        return customerId != null ? customerId.equals(that.customerId) : that.customerId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);

        return result;
    }
}
