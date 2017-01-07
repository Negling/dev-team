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

@Repository("usersDAO")
public class JDBCUsersDAO implements UsersDAO {

    private JdbcOperations jdbcOperations;
    private ResourceBundle sqlBundle;

    @Autowired
    public JDBCUsersDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        this.jdbcOperations = jdbcOperations;
        this.sqlBundle = sqlBundle;
    }

    @Override
    public boolean persists(String email) {
        try {
            return jdbcOperations.queryForObject(sqlBundle.getString("users.persistsByEmail"),
                    (ResultSet rs, int rowNum) -> rs.getString("email"), email) != null;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public boolean persistsByPhone(String phoneNumber) {
        try {
            return jdbcOperations.queryForObject(sqlBundle.getString("users.persistsByPhone"),
                    (ResultSet rs, int rowNum) -> rs.getString("phone"), phoneNumber) != null;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public User getUser(String email) {
        return jdbcOperations.queryForObject(sqlBundle.getString("users.selectUser"), this::mapEntity, email);
    }

    private User mapEntity(ResultSet rs, int row) throws SQLException {
        return new User(rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("password"),
                Role.valueOf(rs.getString("role")));
    }
}
