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

@Repository("projectTaskDAO")
public class JDBCProjectTaskDAO extends JDBCGenericIdentifiedDAO<ProjectTask> implements ProjectTaskDAO {

    @Autowired
    public JDBCProjectTaskDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }

    @Override
    public void update(ProjectTask oldEntity, ProjectTask newEntity) {
        super.jdbcUpdate(sqlBundle.getString("projectTask.update"),
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
        super.jdbcUpdate(sqlBundle.getString("projectTask.delete"), entity.getId());
    }

    @Override
    public void checkStatus(Long taskId) {
        jdbcOperations.update(sqlBundle.getString("projectTask.checkStatus"), taskId);
    }

    @Override
    public void setStatusByProject(Status status, Long projectId) {
        jdbcOperations.update(sqlBundle.getString("projectTask.updateStatusByProject"), status.toString(), projectId);
    }

    @Override
    public ProjectTask getById(Long id) {
        return jdbcOperations.queryForObject(sqlBundle.getString("projectTask.selectById"), this::mapEntity, id);
    }

    @Override
    public List<ProjectTask> getByProject(Long projectId) {
        return jdbcOperations.query(sqlBundle.getString("projectTask.selectByProject"), this::mapEntity, projectId);
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
        final PreparedStatement ps = connection.prepareStatement(sqlBundle.getString("projectTask.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        ps.setLong(1, entity.getProjectId());
        ps.setLong(2, entity.getOperationId());
        ps.setString(3, entity.getName());
        ps.setString(4, entity.getDescription());
        ps.setString(5, entity.getTaskStatus().toString());

        return ps;
    }
}
