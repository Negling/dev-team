package ua.devteam.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.DeveloperDAO;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.enums.DeveloperStatus;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.Developer;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Maps {@link Developer} entity to table named "developers".
 * <p>
 * Fields which belongs to table is:
 * <p><ul>
 * <li> id(id),
 * <li> firstName(first_name),
 * <li> lastName(last_name),
 * <li> email(email),
 * <li> phoneNumber(phone),
 * <li> password(password),
 * <li> hireCost(hire_cost),
 * <li> specialization(specialization),
 * <li> rank(rank),
 * <li> status(status),
 * <li> role(role_id).
 * </ul>
 */
@Repository
public class JDBCDeveloperDAO extends JDBCGenericIdentifiedDAO<Developer> implements DeveloperDAO {

    @Autowired
    public JDBCDeveloperDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }

    @Override
    public void update(Developer oldEntity, Developer newEntity) {
        getJdbcOperations().update(getSqlProperties().getString("developer.update"),
                newEntity.getId(),
                newEntity.getFirstName(),
                newEntity.getLastName(),
                newEntity.getEmail(),
                newEntity.getPhoneNumber(),
                newEntity.getPassword(),
                newEntity.getSpecialization().toString(),
                newEntity.getRank().toString(),
                newEntity.getHireCost(),
                newEntity.getStatus().toString(),
                newEntity.getRole().toString(),
                oldEntity.getId());
    }

    @Override
    public void delete(Developer entity) {
        getJdbcOperations().update(getSqlProperties().getString("developer.delete"), entity.getId());
    }


    @Override
    public void updateStatusByProject(DeveloperStatus status, Long projectId) {
        getJdbcOperations().update(getSqlProperties().getString("developer.updateStatusByProject"), status.toString(), projectId);
    }

    @Override
    public Developer getById(Long id) {
        return getJdbcOperations().queryForObject(getSqlProperties().getString("developer.selectById"), this::mapEntity, id);
    }

    @Override
    public Developer getByEmail(String email) {
        return getJdbcOperations().queryForObject(getSqlProperties().getString("developer.selectByEmail"), this::mapEntity, email);
    }

    @Override
    public List<Developer> getAvailableByParams(DeveloperSpecialization specialization, DeveloperRank rank) {
        return getJdbcOperations().query(getSqlProperties().getString("developer.selectAvailableBySpecAndRank"), this::mapEntity,
                specialization.toString(), rank.toString());
    }

    @Override
    public List<Developer> getAvailableByParams(DeveloperSpecialization specialization, DeveloperRank rank, String lastName) {
        return getJdbcOperations().query(getSqlProperties().getString("developer.selectAvailableBySpecAndRankAndLastname"),
                this::mapEntity, specialization.toString(), rank.toString(), lastName.concat("%"));
    }

    @Override
    public List<Developer> getByParams(DeveloperSpecialization specialization, DeveloperRank rank) {
        return getJdbcOperations().query(getSqlProperties().getString("developer.selectBySpecAndRank"), this::mapEntity,
                specialization.toString(), rank.toString());
    }

    @Override
    public List<Developer> getByParams(DeveloperSpecialization specialization, DeveloperRank rank, String lastName) {
        return getJdbcOperations().query(getSqlProperties().getString("developer.selectBySpecAndRankAndLastname"),
                this::mapEntity, specialization.toString(), rank.toString(), lastName.concat("%"));
    }

    @Override
    protected Developer mapEntity(ResultSet rs, int row) throws SQLException {
        return new
                Developer.Builder()
                .setId(rs.getLong("id"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setEmail(rs.getString("email"))
                .setPhoneNumber(rs.getString("phone"))
                .setPassword(rs.getString("password"))
                .setRole(Role.valueOf(rs.getString("role")))
                .setCurrentTaskId(rs.getLong("active_task_id"))
                .setHireCost(rs.getBigDecimal("hire_cost"))
                .setSpecialization(DeveloperSpecialization.valueOf(rs.getString("specialization")))
                .setRank(DeveloperRank.valueOf(rs.getString("rank")))
                .setStatus(DeveloperStatus.valueOf(rs.getString("status")))
                .build();
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, Developer entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(getSqlProperties().getString("developer.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setString(3, entity.getEmail());
        ps.setString(4, entity.getPhoneNumber());
        ps.setString(5, entity.getPassword());
        ps.setString(6, entity.getSpecialization().toString());
        ps.setString(7, entity.getRank().toString());
        ps.setBigDecimal(8, entity.getHireCost());
        ps.setString(9, entity.getStatus().toString());
        ps.setString(10, entity.getRole().toString());

        return ps;
    }
}
