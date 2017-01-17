package ua.devteam.dao;


import ua.devteam.entity.users.User;

/**
 * Interface to access {@link User} records. User is a superclass of {@link ua.devteam.entity.users.Customer},
 * {@link ua.devteam.entity.users.Manager} and {@link ua.devteam.entity.users.Developer} classes.
 * At most of all its used for general checks and security purposes.
 * {@link ua.devteam.dao.jdbc.JDBCUsersDAO} - implementation.
 */
public interface UsersDAO {

    /**
     * Returns user which match to specified email.
     *
     * @param email query param
     * @return {@link User} subclass with matched email
     * @throws org.springframework.dao.EmptyResultDataAccessException - if there no user with such ID
     */
    User getUser(String email);

    /**
     * Returns true if repository has an user with such email. Email is unique value to all user subclasses.
     *
     * @param email query param
     * @return true if persists or false otherwise
     */
    boolean persistsByEmail(String email);

    /**
     * Returns true if repository has an user with such phone number. Phone number is unique value to all user subclasses.
     *
     * @param phoneNumber query param
     * @return true if persists or false otherwise
     */
    boolean persistsByPhone(String phoneNumber);
}
