package ua.devteam.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.ManagerDAO;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.Manager;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * Maps {@link Manager} entity to table named "managers".
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
public class JDBCManagerDAO extends JDBCGenericIdentifiedDAO<Manager> implements ManagerDAO {

    @Autowired
    public JDBCManagerDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }

    @Override
    public void update(Manager oldEntity, Manager newEntity) {
        getJdbcOperations().update(getSqlProperties().getString("manager.update"),
                newEntity.getId(),
                newEntity.getFirstName(),
                newEntity.getLastName(),
                newEntity.getUsername(),
                newEntity.getPhoneNumber(),
                newEntity.getPassword(),
                newEntity.getRole().toString(),
                oldEntity.getId());
    }

    @Override
    public void delete(Manager entity) {
        getJdbcOperations().update(getSqlProperties().getString("manager.delete"), entity.getId());
    }

    @Override
    public Manager getById(Long id) {
        return getJdbcOperations().queryForObject(getSqlProperties().getString("manager.selectById"), this::mapEntity, id);
    }

    @Override
    public Manager getByEmail(String email) {
        return getJdbcOperations().queryForObject(getSqlProperties().getString("manager.selectByEmail"), this::mapEntity, email);
    }

    @Override
    protected Manager mapEntity(ResultSet rs, int row) throws SQLException {
        return new
                Manager.Builder()
                .setId(rs.getLong("id"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setEmail(rs.getString("email"))
                .setPhoneNumber(rs.getString("phone"))
                .setPassword(rs.getString("password"))
                .setRole(Role.valueOf(rs.getString("role")))
                .setTotalProjectsServed(rs.getLong("projects_served"))
                .build();
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, Manager entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(getSqlProperties().getString("manager.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setString(3, entity.getEmail());
        ps.setString(4, entity.getPhoneNumber());
        ps.setString(5, entity.getPassword());
        ps.setString(6, entity.getRole().toString());

        return ps;
    }
}