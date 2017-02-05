package ua.devteam.dao.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.TaskDevelopmentDataDAO;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.TaskDevelopmentData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Maps {@link TaskDevelopmentData} entity to table named "task_development_data".
 * <p>
 * Fields which belongs to table is:
 * <p><ul>
 * <li> projectTaskId(task_id),
 * <li> developerId(developer_id),
 * <li> specialization(developer_specialization),
 * <li> rank(developer_rank),
 * <li> hoursSpent(hours_spent),
 * <li> status(status).
 * </ul>
 */
@Repository
public class JDBCTaskDevelopmentDataDAO extends JDBCGenericDAO<TaskDevelopmentData> implements TaskDevelopmentDataDAO {

    @Autowired
    public JDBCTaskDevelopmentDataDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }

    @Override
    public void create(TaskDevelopmentData taskDevelopmentData) {
        getJdbcOperations().update(getSqlProperties().getString("taskDevelopmentData.insertSQL"),
                taskDevelopmentData.getProjectTaskId(),
                taskDevelopmentData.getDeveloperId(),
                taskDevelopmentData.getSpecialization().toString(),
                taskDevelopmentData.getRank().toString(),
                taskDevelopmentData.getHoursSpent(),
                taskDevelopmentData.getStatus().toString());
    }

    @Override
    public void createDefault(TaskDevelopmentData taskDevelopmentData) {
        getJdbcOperations().update(getSqlProperties().getString("taskDevelopmentData.insertDefault"),
                taskDevelopmentData.getProjectTaskId(),
                taskDevelopmentData.getDeveloperId(),
                taskDevelopmentData.getSpecialization().toString(),
                taskDevelopmentData.getRank().toString());
    }

    @Override
    public void update(TaskDevelopmentData oldEntity, TaskDevelopmentData newEntity) {
        getJdbcOperations().update(getSqlProperties().getString("taskDevelopmentData.update"),
                newEntity.getProjectTaskId(),
                newEntity.getDeveloperId(),
                newEntity.getSpecialization().toString(),
                newEntity.getRank().toString(),
                newEntity.getHoursSpent(),
                newEntity.getStatus().toString(),
                oldEntity.getProjectTaskId(),
                oldEntity.getDeveloperId());
    }

    @Override
    public void delete(TaskDevelopmentData entity) {
        getJdbcOperations().update(getSqlProperties().getString("taskDevelopmentData.delete"), entity.getProjectTaskId(),
                entity.getDeveloperId());
    }

    @Override
    public void setStatusByProject(Status status, Long projectId) {
        getJdbcOperations().update(getSqlProperties().getString("taskDevelopmentData.updateStatusByProject"), status.toString(), projectId);
    }

    @Override
    public void deleteAllByProject(Long projectId) {
        getJdbcOperations().update(getSqlProperties().getString("taskDevelopmentData.deleteAllByProject"), projectId);
    }

    @Override
    public TaskDevelopmentData getByTaskAndDeveloper(Long taskId, Long developerId) {
        return getJdbcOperations().queryForObject(getSqlProperties().getString("taskDevelopmentData.selectByTaskAndDeveloper"),
                this::mapEntity, taskId, developerId);
    }

    @Override
    public List<TaskDevelopmentData> getByDeveloperAndStatus(Long developerId, Status status) {
        return getJdbcOperations().query(getSqlProperties().getString("taskDevelopmentData.selectByDeveloperAndStatus"),
                this::mapEntity, developerId, status.toString());
    }

    @Override
    public List<TaskDevelopmentData> getAllByTask(Long taskId) {
        return getJdbcOperations().query(getSqlProperties().getString("taskDevelopmentData.selectByTask"), this::mapEntity, taskId);
    }

    @Override
    public List<TaskDevelopmentData> getAllByDeveloper(Long developerId) {
        return getJdbcOperations().query(getSqlProperties().getString("taskDevelopmentData.selectByDeveloper"),
                this::mapEntity, developerId);
    }

    @Override
    protected TaskDevelopmentData mapEntity(ResultSet rs, int row) throws SQLException {
        return new
                TaskDevelopmentData.Builder(
                rs.getLong("task_id"),
                rs.getLong("developer_id"),
                DeveloperSpecialization.valueOf(rs.getString("developer_specialization")),
                DeveloperRank.valueOf(rs.getString("developer_rank")))
                .setTaskName(rs.getString("name"))
                .setTaskDescription(rs.getString("description"))
                .setDeveloperFirstName(rs.getString("first_name"))
                .setDeveloperLastName(rs.getString("last_name"))
                .setHireCost(rs.getBigDecimal("hire_cost"))
                .setHoursSpent(rs.getInt("hours_spent"))
                .setStatus(Status.valueOf(rs.getString("status")))
                .build();
    }
}
