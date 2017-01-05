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

@Repository("developerDAO")
public class JDBCDeveloperDAO extends JDBCGenericIdentifiedDAO<Developer> implements DeveloperDAO {

    @Autowired
    public JDBCDeveloperDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }

    @Override
    public void update(Developer oldEntity, Developer newEntity) {
        super.jdbcUpdate(sqlBundle.getString("developer.update"),
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
                newEntity.getRole().getId(),
                oldEntity.getId());
    }

    @Override
    public void delete(Developer entity) {
        super.jdbcUpdate(sqlBundle.getString("developer.delete"), entity.getId());
    }


    @Override
    public void updateStatusByProject(DeveloperStatus status, Long projectId) {
        jdbcOperations.update(sqlBundle.getString("developer.updateStatusByProject"), status.toString(), projectId);
    }

    @Override
    public Developer getById(Long id) {
        return jdbcOperations.queryForObject(sqlBundle.getString("developer.selectById"), this::mapEntity, id);
    }

    @Override
    public Developer getByEmail(String email) {
        return jdbcOperations.queryForObject(sqlBundle.getString("developer.selectByEmail"), this::mapEntity, email);
    }

    @Override
    public List<Developer> getAvailableByParams(DeveloperSpecialization specialization, DeveloperRank rank) {
        return jdbcOperations.query(sqlBundle.getString("developer.selectAvailableBySpecAndRank"), this::mapEntity,
                specialization.toString(), rank.toString());
    }

    @Override
    public List<Developer> getAvailableByParams(DeveloperSpecialization specialization, DeveloperRank rank, String lastName) {
        return jdbcOperations.query(sqlBundle.getString("developer.selectAvailableBySpecAndRankAndLastname"), this::mapEntity,
                specialization.toString(), rank.toString(), lastName.concat("%"));
    }

    @Override
    public List<Developer> getByParams(DeveloperSpecialization specialization, DeveloperRank rank) {
        return jdbcOperations.query(sqlBundle.getString("developer.selectBySpecAndRank"), this::mapEntity,
                specialization.toString(), rank.toString());
    }

    @Override
    public List<Developer> getByParams(DeveloperSpecialization specialization, DeveloperRank rank, String lastName) {
        return jdbcOperations.query(sqlBundle.getString("developer.selectBySpecAndRankAndLastname"), this::mapEntity,
                specialization.toString(), rank.toString(), lastName.concat("%"));
    }

    @Override
    protected Developer mapEntity(ResultSet rs, int row) throws SQLException {
        return new Developer(rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("password"),
                Role.valueOf(rs.getString("role")),
                rs.getLong("active_task_id"),
                rs.getBigDecimal("hire_cost"),
                DeveloperSpecialization.valueOf(rs.getString("specialization")),
                DeveloperRank.valueOf(rs.getString("rank")),
                DeveloperStatus.valueOf(rs.getString("status")));
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, Developer entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sqlBundle.getString("developer.insertSQL"),
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
        ps.setInt(10, entity.getRole().getId());

        return ps;
    }
}
