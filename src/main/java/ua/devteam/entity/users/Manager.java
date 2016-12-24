package ua.devteam.entity.users;

import ua.devteam.entity.enums.Role;

import java.io.Serializable;

public class Manager extends User implements Serializable {
    private Long totalProjectsServed;

    public Manager() {
    }

    public Manager(String firstName, String lastName, String email, String phoneNumber, String password, Role role,
                   Long totalProjectsServed) {

        this(null, firstName, lastName, email, phoneNumber, password, role, totalProjectsServed);
    }

    public Manager(Long id, String firstName, String lastName, String email, String phoneNumber, String password,
                   Role role, Long totalProjectsServed) {

        super(id, firstName, lastName, email, phoneNumber, password, role);
        this.totalProjectsServed = totalProjectsServed;
    }

    @Override
    public void setRole(Role role) {
        super.setRole(role);
    }

    public Long getTotalProjectsServed() {
        return totalProjectsServed;
    }

    public void setTotalProjectsServed(Long totalProjectsServed) {
        this.totalProjectsServed = totalProjectsServed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Manager manager = (Manager) o;

        return totalProjectsServed != null ?
                totalProjectsServed.equals(manager.totalProjectsServed) : manager.totalProjectsServed == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (totalProjectsServed != null ? totalProjectsServed.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "totalProjectsServed=" + totalProjectsServed +
                "} " + super.toString();
    }
}
