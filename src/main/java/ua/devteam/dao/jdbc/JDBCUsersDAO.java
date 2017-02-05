package ua.devteam.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.UsersDAO;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Maps {@link User} entity from any of next tables: "customers", "managers", "developers".
 * <p>
 * Fields which belongs to table is:
 * <p><ul>
 * <li> id(id),
 * <li> firstName(first_name),
 * <li> lastName(last_name),
 * <li> email(email),
 * <li> phoneNumber(phone),
 * <li> password(password),
 * <li> role(role_id).
 * </ul>
 */
@Repository
public class JDBCUsersDAO extends JDBCGenericDAO<User> implements UsersDAO {

    @Autowired
    public JDBCUsersDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }

    @Override
    public boolean persistsByEmail(String email) {
        try {
            return getJdbcOperations().queryForObject(getSqlProperties().getString("users.persistsByEmail"),
                    (ResultSet rs, int rowNum) -> rs.getString("email"), email) != null;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public boolean persistsByPhone(String phoneNumber) {
        try {
            return getJdbcOperations().queryForObject(getSqlProperties().getString("users.persistsByPhone"),
                    (ResultSet rs, int rowNum) -> rs.getString("phone"), phoneNumber) != null;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public User getUser(String email) {
        return getJdbcOperations().queryForObject(getSqlProperties().getString("users.selectUser"), this::mapEntity, email);
    }

    @Override
    protected User mapEntity(ResultSet rs, int row) throws SQLException {
        return new
                User.Builder()
                .setId(rs.getLong("id"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setEmail(rs.getString("email"))
                .setPhoneNumber(rs.getString("phone"))
                .setPassword(rs.getString("password"))
                .setRole(Role.valueOf(rs.getString("role")))
                .build();
    }
}
