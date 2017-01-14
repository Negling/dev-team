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

@Repository("taskDevelopmentDataDAO")
public class JDBCTaskDevelopmentDataDAO extends JDBCGenericDAO<TaskDevelopmentData> implements TaskDevelopmentDataDAO {

    @Autowired
    public JDBCTaskDevelopmentDataDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }

    @Override
    public void create(TaskDevelopmentData entity) {
        super.jdbcUpdate(sqlBundle.getString("taskDevelopmentData.insertSQL"),
                entity.getProjectTaskId(),
                entity.getDeveloperId(),
                entity.getSpecialization().toString(),
                entity.getRank().toString(),
                entity.getHoursSpent(),
                entity.getStatus().toString());
    }

    @Override
    public void createDefault(TaskDevelopmentData entity) {
        super.jdbcUpdate(sqlBundle.getString("taskDevelopmentData.insertDefault"), entity.getProjectTaskId(),
                entity.getDeveloperId(), entity.getSpecialization().toString(), entity.getRank().toString());
    }

    @Override
    public void update(TaskDevelopmentData oldEntity, TaskDevelopmentData newEntity) {
        super.jdbcUpdate(sqlBundle.getString("taskDevelopmentData.update"),
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
        super.jdbcUpdate(sqlBundle.getString("taskDevelopmentData.delete"), entity.getProjectTaskId(),
                entity.getDeveloperId());
    }

    @Override
    public void setStatusByProject(Status status, Long projectId) {
        jdbcOperations.update(sqlBundle.getString("taskDevelopmentData.updateStatusByProject"), status.toString(), projectId);
    }

    @Override
    public void deleteAllByProject(Long projectId) {
        jdbcOperations.update(sqlBundle.getString("taskDevelopmentData.deleteAllByProject"), projectId);
    }

    @Override
    public TaskDevelopmentData getByTaskAndDeveloper(Long taskId, Long developerId) {
        return jdbcOperations.queryForObject(sqlBundle.getString("taskDevelopmentData.selectByTaskAndDeveloper"), this::mapEntity,
                taskId, developerId);
    }

    @Override
    public List<TaskDevelopmentData> getByDeveloperAndStatus(Long developerId, Status status) {
        return jdbcOperations.query(sqlBundle.getString("taskDevelopmentData.selectByDeveloperAndStatus"), this::mapEntity,
                developerId, status.toString());
    }

    @Override
    public List<TaskDevelopmentData> getAllByTask(Long taskId) {
        return jdbcOperations.query(sqlBundle.getString("taskDevelopmentData.selectByTask"), this::mapEntity, taskId);
    }

    @Override
    public List<TaskDevelopmentData> getAllByDeveloper(Long developerId) {
        return jdbcOperations.query(sqlBundle.getString("taskDevelopmentData.selectByDeveloper"), this::mapEntity, developerId);
    }

    @Override
    protected TaskDevelopmentData mapEntity(ResultSet rs, int row) throws SQLException {
        return new TaskDevelopmentData(rs.getLong("task_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getLong("developer_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                DeveloperSpecialization.valueOf(rs.getString("developer_specialization")),
                DeveloperRank.valueOf(rs.getString("developer_rank")),
                rs.getBigDecimal("hire_cost"),
                rs.getInt("hours_spent"),
                Status.valueOf(rs.getString("status")));
    }
}
