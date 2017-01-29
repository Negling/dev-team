package ua.devteam.entity.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.projects.Check;

import java.io.Serializable;
import java.util.List;

/**
 * User subclass. {@link User}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class Customer extends User implements Serializable {

    /* User checks history */
    private List<Check> checks;

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
}
