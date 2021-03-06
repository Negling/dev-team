package ua.devteam.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.OperationDAO;
import ua.devteam.entity.tasks.Operation;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Maps {@link Operation} entity to table named "technical_task_operations".
 * <p>
 * Fields which belongs to table is:
 * <p><ul>
 * <li> id(id),
 * <li> technicalTaskId(technical_task_id),
 * <li> name(name),
 * <li> description(description).
 * </ul>
 */
@Repository("operationDAO")
public class JDBCOperationDAO extends JDBCGenericIdentifiedDAO<Operation> implements OperationDAO {

    @Autowired
    public JDBCOperationDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }

    @Override
    public void update(Operation oldEntity, Operation newEntity) {
        getJdbcOperations().update(getSqlProperties().getString("operations.update"),
                newEntity.getId(),
                newEntity.getTechnicalTaskId(),
                newEntity.getName(),
                newEntity.getDescription(),
                oldEntity.getId());
    }

    @Override
    public void delete(Operation entity) {
        getJdbcOperations().update(getSqlProperties().getString("operations.delete"), entity.getId());
    }

    @Override
    public Operation getById(Long id) {
        return getJdbcOperations().queryForObject(getSqlProperties().getString("operations.selectById"), this::mapEntity, id);
    }

    @Override
    public List<Operation> getByTechnicalTask(Long technicalTaskId) {
        return getJdbcOperations().query(getSqlProperties().getString("operations.selectByTechnicalTask"), this::mapEntity, technicalTaskId);
    }

    @Override
    protected Operation mapEntity(ResultSet rs, int row) throws SQLException {
        return new
                Operation.Builder(
                rs.getLong("technical_task_id"),
                rs.getString("name"),
                rs.getString("description")).
                setId(rs.getLong("id"))
                .build();
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, Operation entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(getSqlProperties().getString("operations.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        ps.setLong(1, entity.getTechnicalTaskId());
        ps.setString(2, entity.getName());
        ps.setString(3, entity.getDescription());

        return ps;
    }
}
