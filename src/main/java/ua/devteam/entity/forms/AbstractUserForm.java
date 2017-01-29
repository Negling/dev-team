package ua.devteam.entity.forms;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.devteam.entity.users.User;

/**
 * Simple abstract form that maps user data from view inputs.
 *
 * @param <T> entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
abstract class AbstractUserForm<T extends User> {
    /* User first name */
    private String firstName;
    /* User last name */
    private String lastName;
    /* User email */
    private String email;
    /* User phone number */
    private String phoneNumber;
    /* User password */
    private String password;
    /* User confirmed password */
    private String confirmedPassword;

    public abstract T getEntity();
}
