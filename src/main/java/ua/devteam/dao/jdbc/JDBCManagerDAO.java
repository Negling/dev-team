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
 * <p>
 * id(id),
 * firstName(first_name),
 * lastName(last_name),
 * email(email),
 * phoneNumber(phone),
 * password(password),
 * role(role_id).
 */
@Repository("managerDAO")
public class JDBCManagerDAO extends JDBCGenericIdentifiedDAO<Manager> implements ManagerDAO {

    @Autowired
    public JDBCManagerDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }

    @Override
    public void update(Manager oldEntity, Manager newEntity) {
        jdbcOperations.update(sqlBundle.getString("manager.update"),
                newEntity.getId(),
                newEntity.getFirstName(),
                newEntity.getLastName(),
                newEntity.getUsername(),
                newEntity.getPhoneNumber(),
                newEntity.getPassword(),
                newEntity.getRole().getId(),
                oldEntity.getId());
    }

    @Override
    public void delete(Manager entity) {
        jdbcOperations.update(sqlBundle.getString("manager.delete"), entity.getId());
    }

    @Override
    public Manager getById(Long id) {
        return jdbcOperations.queryForObject(sqlBundle.getString("manager.selectById"), this::mapEntity, id);
    }

    @Override
    public Manager getByEmail(String email) {
        return jdbcOperations.queryForObject(sqlBundle.getString("manager.selectByEmail"), this::mapEntity, email);
    }

    @Override
    protected Manager mapEntity(ResultSet rs, int row) throws SQLException {
        return new Manager(rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("password"),
                Role.valueOf(rs.getString("role")),
                rs.getLong("projects_served"));
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, Manager entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sqlBundle.getString("manager.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setString(3, entity.getEmail());
        ps.setString(4, entity.getPassword());
        ps.setString(5, entity.getPhoneNumber());
        ps.setInt(6, entity.getRole().getId());

        return ps;
    }
}