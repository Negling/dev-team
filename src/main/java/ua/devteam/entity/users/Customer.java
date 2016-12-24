package ua.devteam.entity.users;

import ua.devteam.entity.enums.Role;

import java.util.List;

public class Customer extends User {
    private List<Check> checks;

    public Customer() {
    }

    public Customer(Long id, String firstName, String lastName, String email, String phoneNumber, String password, Role role) {
        this(id, firstName, lastName, email, phoneNumber, password, role, null);
    }

    public Customer(String firstName, String lastName, String email, String phoneNumber, String password, Role role) {
        this(null, firstName, lastName, email, phoneNumber, password, role);
    }

    public Customer(Long id, String firstName, String lastName, String email, String phoneNumber, String password,
                    Role role, List<Check> checks) {
        super(id, firstName, lastName, email, phoneNumber, password, role);
        this.checks = checks;
    }

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        return checks != null ? checks.equals(customer.checks) : customer.checks == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (checks != null ? checks.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "checks=" + checks +
                "} " + super.toString();
    }
}
