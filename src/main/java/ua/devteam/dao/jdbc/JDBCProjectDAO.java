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

@Repository("projectDAO")
public class JDBCProjectDAO extends JDBCGenericIdentifiedDAO<Project> implements ProjectDAO {

    @Autowired
    public JDBCProjectDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }

    @Override
    public void update(Project oldEntity, Project newEntity) {
        super.jdbcUpdate(sqlBundle.getString("project.update"),
                newEntity.getId(),
                newEntity.getTechnicalTaskId(),
                newEntity.getCustomerId(),
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
        super.jdbcUpdate(sqlBundle.getString("project.delete"), entity.getId());
    }

    @Override
    public Project getById(Long projectId) {
        return jdbcOperations.queryForObject(sqlBundle.getString("project.selectById"), this::mapEntity, projectId);
    }

    @Override
    public List<Project> getAllByManager(Long managerId) {
        return jdbcOperations.query(sqlBundle.getString("project.selectByManager"), this::mapEntity, managerId);
    }

    @Override
    public List<Project> getByManagerAndStatus(Long managerId, Status status) {
        return jdbcOperations.query(sqlBundle.getString("project.selectByManagerAndStatus"), this::mapEntity, managerId, status.toString());
    }

    @Override
    public List<Project> getCompleteByManager(Long managerId) {
        return jdbcOperations.query(sqlBundle.getString("project.selectCompleteByManager"), this::mapEntity, managerId);
    }

    @Override
    protected Project mapEntity(ResultSet rs, int row) throws SQLException {
        return new Project(rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getLong("customer_id"),
                rs.getLong("manager_id"),
                rs.getString("manager_commentary"),
                rs.getLong("technical_task_id"),
                rs.getBigDecimal("totalCost"),
                rs.getDate("start_date"),
                rs.getDate("end_date"),
                Status.valueOf(rs.getString("status")));
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, Project entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sqlBundle.getString("project.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        Date startDate = entity.getStartDate() == null ? null : new Date(entity.getStartDate().getTime());

        ps.setLong(1, entity.getTechnicalTaskId());
        ps.setLong(2, entity.getCustomerId());
        ps.setLong(3, entity.getManagerId());
        ps.setString(4, entity.getName());
        ps.setString(5, entity.getDescription());
        ps.setDate(7, startDate);
        ps.setString(9, entity.getStatus().toString());

        if (entity.getManagerCommentary() == null) {
            ps.setNull(6, Types.VARCHAR);
        } else {
            ps.setString(6, entity.getManagerCommentary());
        }
        if (entity.getEndDate() == null) {
            ps.setNull(8, Types.TIMESTAMP);
        } else {
            ps.setDate(8, new Date(entity.getEndDate().getTime()));
        }

        return ps;
    }
}
