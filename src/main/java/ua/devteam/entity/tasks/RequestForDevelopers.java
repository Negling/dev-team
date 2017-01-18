package ua.devteam.entity.tasks;

import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;

import java.io.Serializable;

/**
 * This class represents information about developers that requested by customer to perform specific project operation.
 */
public class RequestForDevelopers implements Serializable {

    /* Id of operation that this request is bound to */
    private Long operationId;
    /* Specialization of developers */
    private DeveloperSpecialization specialization;
    /* Rank of developers */
    private DeveloperRank rank;
    /* Quantity of developers. May accept values between 1 and 100. */
    private Integer quantity;

    public RequestForDevelopers() {
    }

    public RequestForDevelopers(Long operationId, DeveloperSpecialization specialization, DeveloperRank rank, Integer quantity) {
        this.operationId = operationId;
        this.specialization = specialization;
        this.rank = rank;
        this.quantity = quantity;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public DeveloperSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(DeveloperSpecialization specialization) {
        this.specialization = specialization;
    }

    public DeveloperRank getRank() {
        return rank;
    }

    public void setRank(DeveloperRank rank) {
        this.rank = rank;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestForDevelopers that = (RequestForDevelopers) o;

        if (operationId != null ? !operationId.equals(that.operationId) : that.operationId != null) return false;
        if (specialization != that.specialization) return false;
        if (rank != that.rank) return false;

        return quantity != null ? quantity.equals(that.quantity) : that.quantity == null;
    }

    @Override
    public int hashCode() {
        int result = operationId != null ? operationId.hashCode() : 0;
        result = 31 * result + (specialization != null ? specialization.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "RequestForDevelopers{" +
                "operationId=" + operationId +
                ", specialization=" + specialization +
                ", rank=" + rank +
                ", quantity=" + quantity +
                '}';
    }
}
