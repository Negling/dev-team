package ua.devteam.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.ProjectTaskDAO;
import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.ProjectTask;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Maps {@link ProjectTask} entity to table named "project_tasks".
 * <p>
 * Fields which belongs to table is:
 * <p><ul>
 * <li> id(id),
 * <li> name(name),
 * <li> description(description),
 * <li> projectId(project_id),
 * <li> operationId(operation_id),
 * <li> taskStatus(status).
 * </ul>
 */
@Repository
public class JDBCProjectTaskDAO extends JDBCGenericIdentifiedDAO<ProjectTask> implements ProjectTaskDAO {

    @Autowired
    public JDBCProjectTaskDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }

    @Override
    public void update(ProjectTask oldEntity, ProjectTask newEntity) {
        jdbcOperations.update(sqlProperties.getString("projectTask.update"),
                newEntity.getId(),
                newEntity.getProjectId(),
                newEntity.getOperationId(),
                newEntity.getName(),
                newEntity.getDescription(),
                newEntity.getTaskStatus().toString(),
                oldEntity.getId());
    }

    @Override
    public void delete(ProjectTask entity) {
        jdbcOperations.update(sqlProperties.getString("projectTask.delete"), entity.getId());
    }

    @Override
    public void checkStatus(Long taskId) {
        jdbcOperations.update(sqlProperties.getString("projectTask.checkStatus"), taskId);
    }

    @Override
    public void setStatusByProject(Status status, Long projectId) {
        jdbcOperations.update(sqlProperties.getString("projectTask.updateStatusByProject"), status.toString(), projectId);
    }

    @Override
    public ProjectTask getById(Long id) {
        return jdbcOperations.queryForObject(sqlProperties.getString("projectTask.selectById"), this::mapEntity, id);
    }

    @Override
    public List<ProjectTask> getByProject(Long projectId) {
        return jdbcOperations.query(sqlProperties.getString("projectTask.selectByProject"), this::mapEntity, projectId);
    }

    @Override
    protected ProjectTask mapEntity(ResultSet rs, int row) throws SQLException {
        return new ProjectTask(rs.getLong("id"),
                rs.getLong("project_id"),
                rs.getLong("operation_id"),
                rs.getString("name"),
                rs.getString("description"),
                Status.valueOf(rs.getString("status")),
                rs.getInt("total_hours_spent"));
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, ProjectTask entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sqlProperties.getString("projectTask.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        ps.setLong(1, entity.getProjectId());
        ps.setLong(2, entity.getOperationId());
        ps.setString(3, entity.getName());
        ps.setString(4, entity.getDescription());
        ps.setString(5, entity.getTaskStatus().toString());

        return ps;
    }
}
