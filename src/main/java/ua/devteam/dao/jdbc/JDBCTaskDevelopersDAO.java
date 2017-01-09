package ua.devteam.dao.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.TaskDevelopersDAO;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.TaskDeveloper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

@Repository("taskDevelopersDAO")
public class JDBCTaskDevelopersDAO extends JDBCGenericDAO<TaskDeveloper> implements TaskDevelopersDAO {

    @Autowired
    public JDBCTaskDevelopersDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }

    @Override
    public void create(TaskDeveloper entity) {
        super.jdbcUpdate(sqlBundle.getString("taskDevelopers.insertSQL"),
                entity.getProjectTaskId(),
                entity.getDeveloperId(),
                entity.getHoursSpent(),
                entity.getStatus().toString());
    }

    @Override
    public void createDefault(TaskDeveloper entity) {
        super.jdbcUpdate(sqlBundle.getString("taskDevelopers.insertDefault"), entity.getProjectTaskId(),
                entity.getDeveloperId());
    }

    @Override
    public void update(TaskDeveloper oldEntity, TaskDeveloper newEntity) {
        super.jdbcUpdate(sqlBundle.getString("taskDevelopers.update"),
                newEntity.getProjectTaskId(),
                newEntity.getDeveloperId(),
                newEntity.getHoursSpent(),
                newEntity.getStatus().toString(),
                oldEntity.getProjectTaskId(),
                oldEntity.getDeveloperId());
    }

    @Override
    public void delete(TaskDeveloper entity) {
        super.jdbcUpdate(sqlBundle.getString("taskDevelopers.delete"), entity.getProjectTaskId(),
                entity.getDeveloperId());
    }

    @Override
    public void setStatusByProject(Status status, Long projectId) {
        jdbcOperations.update(sqlBundle.getString("taskDevelopers.updateStatusByProject"), status.toString(), projectId);
    }

    @Override
    public void deleteAllByProject(Long projectId) {
        jdbcOperations.update(sqlBundle.getString("taskDevelopers.deleteAllByProject"), projectId);
    }

    @Override
    public TaskDeveloper getByTaskAndDeveloper(Long taskId, Long developerId) {
        return jdbcOperations.queryForObject(sqlBundle.getString("taskDevelopers.selectByTaskAndDeveloper"), this::mapEntity,
                taskId, developerId);
    }

    @Override
    public TaskDeveloper getByDeveloperAndStatus(Long developerId, Status status) {
        return jdbcOperations.queryForObject(sqlBundle.getString("taskDevelopers.selectByDeveloperAndStatus"), this::mapEntity,
                developerId, status.toString());
    }

    @Override
    public List<TaskDeveloper> getAllByTask(Long taskId) {
        return jdbcOperations.query(sqlBundle.getString("taskDevelopers.selectByTask"), this::mapEntity, taskId);
    }

    @Override
    public List<TaskDeveloper> getAllByProject(Long projectId) {
        return jdbcOperations.query(sqlBundle.getString("taskDevelopers.selectByProject"), this::mapEntity, projectId);
    }

    @Override
    protected TaskDeveloper mapEntity(ResultSet rs, int row) throws SQLException {

        return new TaskDeveloper(rs.getLong("task_id"),
                rs.getLong("developer_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                DeveloperSpecialization.valueOf(rs.getString("specialization")),
                DeveloperRank.valueOf(rs.getString("rank")),
                rs.getBigDecimal("hire_cost"),
                rs.getInt("hours_spent"),
                Status.valueOf(rs.getString("status")));
    }
}
