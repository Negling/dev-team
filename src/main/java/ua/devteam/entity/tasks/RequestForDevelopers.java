package ua.devteam.entity.tasks;

import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;

import java.io.Serializable;

public class RequestForDevelopers implements Serializable {
    private Long operation_id;
    private DeveloperSpecialization specialization;
    private DeveloperRank rank;
    private Integer quantity;

    public RequestForDevelopers() {
    }

    public RequestForDevelopers(Long operation_id, DeveloperSpecialization specialization, DeveloperRank rank, Integer quantity) {
        this.operation_id = operation_id;
        this.specialization = specialization;
        this.rank = rank;
        this.quantity = quantity;
    }

    public Long getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(Long operation_id) {
        this.operation_id = operation_id;
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

        if (operation_id != null ? !operation_id.equals(that.operation_id) : that.operation_id != null) return false;
        if (specialization != that.specialization) return false;
        if (rank != that.rank) return false;

        return quantity != null ? quantity.equals(that.quantity) : that.quantity == null;
    }

    @Override
    public int hashCode() {
        int result = operation_id != null ? operation_id.hashCode() : 0;
        result = 31 * result + (specialization != null ? specialization.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "RequestForDevelopers{" +
                "operation_id=" + operation_id +
                ", specialization=" + specialization +
                ", rank=" + rank +
                ", quantity=" + quantity +
                '}';
    }
}
