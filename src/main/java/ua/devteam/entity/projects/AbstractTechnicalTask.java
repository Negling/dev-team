package ua.devteam.entity.projects;


import ua.devteam.entity.AbstractTask;
import ua.devteam.entity.enums.Status;

import java.io.Serializable;

public abstract class AbstractTechnicalTask extends AbstractTask implements Serializable{
    private Long customerId;
    private Status status;
    private String managerCommentary;

    public AbstractTechnicalTask() {
    }

    public AbstractTechnicalTask(Long id, String name, String description, Long customerId, Status status, String managerCommentary) {
        super(id, name, description);
        this.customerId = customerId;
        this.status = status;
        this.managerCommentary = managerCommentary;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getManagerCommentary() {
        return managerCommentary;
    }

    public void setManagerCommentary(String managerCommentary) {
        this.managerCommentary = managerCommentary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTechnicalTask)) return false;
        if (!super.equals(o)) return false;

        AbstractTechnicalTask that = (AbstractTechnicalTask) o;

        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (status != that.status) return false;

        return managerCommentary != null ? managerCommentary.equals(that.managerCommentary) : that.managerCommentary == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (managerCommentary != null ? managerCommentary.hashCode() : 0);

        return result;
    }


}
