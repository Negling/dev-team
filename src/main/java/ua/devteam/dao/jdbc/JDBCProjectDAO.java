package ua.devteam.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.ProjectDAO;
import ua.devteam.entity.enums.Status;
import ua.devteam.entity.projects.Project;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Maps {@link Project} entity to table named "projects".
 * <p>
 * Fields which belongs to table is:
 * <p><ul>
 * <li> id(id),
 * <li> name(name),
 * <li> description(description),
 * <li> status(status),
 * <li> managerCommentary(manager_commentary),
 * <li> managerId(manager_id),
 * <li> technicalTaskId(technical_task_id),
 * <li> startDate(start_date),
 * <li> endDate(end_date).
 * </ul>
 */
@Repository
public class JDBCProjectDAO extends JDBCGenericIdentifiedDAO<Project> implements ProjectDAO {

    @Autowired
    public JDBCProjectDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }

    @Override
    public void update(Project oldEntity, Project newEntity) {
        jdbcOperations.update(sqlProperties.getString("project.update"),
                newEntity.getId(),
                newEntity.getTechnicalTaskId(),
                newEntity.getManagerId(),
                newEntity.getName(),
                newEntity.getDescription(),
                newEntity.getManagerCommentary(),
                newEntity.getStartDate(),
                newEntity.getEndDate(),
                newEntity.getStatus().toString(),
                oldEntity.getId());
    }

    @Override
    public void delete(Project entity) {
        jdbcOperations.update(sqlProperties.getString("project.delete"), entity.getId());
    }

    @Override
    public Project getById(Long projectId) {
        return jdbcOperations.queryForObject(sqlProperties.getString("project.selectById"), this::mapEntity, projectId);
    }

    @Override
    public void updateStatus(Long projectId, Status status) {
        jdbcOperations.update(sqlProperties.getString("project.updateStatus"), projectId, status.toString());
    }

    @Override
    public List<Project> getAllByManager(Long managerId) {
        return jdbcOperations.query(sqlProperties.getString("project.selectByManager"), this::mapEntity, managerId);
    }

    @Override
    public List<Project> getByManagerAndStatus(Long managerId, Status status) {
        return jdbcOperations.query(sqlProperties.getString("project.selectByManagerAndStatus"), this::mapEntity, managerId, status.toString());
    }

    @Override
    public List<Project> getCompleteByManager(Long managerId) {
        return jdbcOperations.query(sqlProperties.getString("project.selectCompleteByManager"), this::mapEntity, managerId);
    }

    @Override
    public List<Project> getRunningByManager(Long managerId) {
        return jdbcOperations.query(sqlProperties.getString("project.selectRunningByManager"), this::mapEntity, managerId);
    }

    @Override
    public List<Project> getCompleteByCustomer(Long customerId) {
        return jdbcOperations.query(sqlProperties.getString("project.selectCompleteByCustomer"), this::mapEntity, customerId);
    }

    @Override
    public List<Project> getRunningByCustomer(Long customerId) {
        return jdbcOperations.query(sqlProperties.getString("project.selectRunningByCustomer"), this::mapEntity, customerId);
    }

    @Override
    protected Project mapEntity(ResultSet rs, int row) throws SQLException {
        return new Project.Builder(rs.getString("name"),
                rs.getString("description"),
                rs.getLong("customer_id"),
                rs.getLong("manager_id"))
                .setId(rs.getLong("id"))
                .setManagerCommentary(rs.getString("manager_commentary"))
                .setTechnicalTaskId(rs.getLong("technical_task_id"))
                .setTotalProjectCost(rs.getBigDecimal("totalCost"))
                .setStartDate(rs.getDate("start_date"))
                .setEndDate(rs.getDate("end_date"))
                .setStatus(Status.valueOf(rs.getString("status")))
                .build();
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, Project entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sqlProperties.getString("project.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        Date startDate = entity.getStartDate() == null ? null : new Date(entity.getStartDate().getTime());

        ps.setLong(1, entity.getTechnicalTaskId());
        ps.setLong(2, entity.getManagerId());
        ps.setString(3, entity.getName());
        ps.setString(4, entity.getDescription());
        ps.setDate(6, startDate);
        ps.setString(8, entity.getStatus().toString());

        if (entity.getManagerCommentary() == null) {
            ps.setNull(5, Types.VARCHAR);
        } else {
            ps.setString(5, entity.getManagerCommentary());
        }
        if (entity.getEndDate() == null) {
            ps.setNull(7, Types.TIMESTAMP);
        } else {
            ps.setDate(7, new Date(entity.getEndDate().getTime()));
        }

        return ps;
    }
}
