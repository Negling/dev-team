package ua.devteam.entity.projects;


import ua.devteam.entity.AbstractTask;
import ua.devteam.entity.enums.Status;

import java.io.Serializable;

/**
 * Task of high-tier. Contains id of submitted it customer, current status and commentary of manager, in case of declining.
 */
abstract class AbstractTechnicalTask extends AbstractTask implements Serializable {
    /* Id of customer that submitted this task */
    private Long customerId;
    /* Current status of task */
    private Status status;
    /* Commentary of manager. In most cases uses to describe reason of decline. Can be null or empty as well */
    private String managerCommentary;

    AbstractTechnicalTask() {
    }

    AbstractTechnicalTask(Long id, String name, String description, Long customerId, Status status, String managerCommentary) {
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
