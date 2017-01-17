package ua.devteam.dao;

import ua.devteam.entity.users.Manager;

/**
 * Simple interface to access {@link Manager} records.
 * {@link ua.devteam.dao.jdbc.JDBCManagerDAO} - implementation.
 */
public interface ManagerDAO extends GenericDAO<Manager>, Identified<Manager> {

    /**
     * Returns manager entity which match to specified email.
     *
     * @param email query param
     * @return {@link Manager} entity
     * @throws org.springframework.dao.EmptyResultDataAccessException - if there no entity with such ID.
     */
    Manager getByEmail(String email);
}
