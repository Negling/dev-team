package ua.devteam.entity.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.enums.Role;

import java.io.Serializable;

/**
 * User subclass. As additional field contains counter of total served projects.
 * {@link User}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class Manager extends User implements Serializable {

    /* Total amount of projects that this manager served */
    private Long totalProjectsServed;

    public Manager(String firstName, String lastName, String email, String phoneNumber, String password, Role role,
                   Long totalProjectsServed) {

        this(null, firstName, lastName, email, phoneNumber, password, role, totalProjectsServed);
    }

    public Manager(Long id, String firstName, String lastName, String email, String phoneNumber, String password,
                   Role role, Long totalProjectsServed) {

        super(id, firstName, lastName, email, phoneNumber, password, role);
        this.totalProjectsServed = totalProjectsServed;
    }
}
