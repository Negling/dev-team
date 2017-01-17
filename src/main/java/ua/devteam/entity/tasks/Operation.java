package ua.devteam.entity.tasks;


import ua.devteam.entity.AbstractTask;

import java.io.Serializable;
import java.util.List;

public class Operation extends AbstractTask implements Serializable{
    private Long technicalTaskId;
    private List<RequestForDevelopers> requestsForDevelopers;

    public Operation() {
    }

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

    public Long getTechnicalTaskId() {
        return technicalTaskId;
    }

    public void setTechnicalTaskId(Long technicalTaskId) {
        this.technicalTaskId = technicalTaskId;
    }

    public List<RequestForDevelopers> getRequestsForDevelopers() {
        return requestsForDevelopers;
    }

    public void setRequestsForDevelopers(List<RequestForDevelopers> requestsForDevelopers) {
        this.requestsForDevelopers = requestsForDevelopers;
    }

    @Override
    public void setDeepId(Long id) {
        setId(id);

        requestsForDevelopers.forEach(requestForDevelopers -> requestForDevelopers.setOperationId(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        if (!super.equals(o)) return false;

        Operation operation = (Operation) o;

        if (technicalTaskId != null ? !technicalTaskId.equals(operation.technicalTaskId) : operation.technicalTaskId != null)
            return false;

        return requestsForDevelopers != null ? requestsForDevelopers.equals(operation.requestsForDevelopers)
                : operation.requestsForDevelopers == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (technicalTaskId != null ? technicalTaskId.hashCode() : 0);
        result = 31 * result + (requestsForDevelopers != null ? requestsForDevelopers.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Operation{");
        sb.append("name=").append(this.getName());
        sb.append(", description=").append(this.getDescription());
        sb.append(", technicalTaskId=").append(technicalTaskId);
        sb.append(", requestsForDevelopers=").append(requestsForDevelopers);
        sb.append('}');
        return sb.toString();
    }
}
