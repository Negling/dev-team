package ua.devteam.dao.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.TechnicalTaskDAO;
import ua.devteam.entity.projects.TechnicalTask;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

@Repository("technicalTaskDAO")
public class JDBCTechnicalTaskDAO extends JDBCGenericIdentifiedDAO<TechnicalTask> implements TechnicalTaskDAO {

    @Autowired
    public JDBCTechnicalTaskDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }

    @Override
    public void update(TechnicalTask oldEntity, TechnicalTask newEntity) {
        super.jdbcUpdate(sqlBundle.getString("technicalTask.update"),
                newEntity.getId(),
                newEntity.getCustomerId(),
                newEntity.getManagerId(),
                newEntity.getName(),
                newEntity.getDescription(),
                oldEntity.getId());
    }

    @Override
    public void delete(TechnicalTask entity) {
        super.jdbcUpdate(sqlBundle.getString("technicalTask.delete"), entity.getId());
    }

    @Override
    public TechnicalTask getById(Long id) {
        return jdbcOperations.queryForObject(sqlBundle.getString("technicalTask.selectById"), this::mapEntity, id);
    }

    @Override
    public List<TechnicalTask> getAll() {
        return jdbcOperations.query(sqlBundle.getString("technicalTask.selectAll"), this::mapEntity);
    }

    @Override
    public List<TechnicalTask> getByManager(Long managerId) {
        return jdbcOperations.query(sqlBundle.getString("technicalTask.selectByManager"), this::mapEntity, managerId);
    }

    @Override
    protected TechnicalTask mapEntity(ResultSet rs, int row) throws SQLException {
        return new TechnicalTask(rs.getLong("id"),
                rs.getLong("customer_id"),
                rs.getLong("manager_id"),
                rs.getString("name"),
                rs.getString("description"));
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, TechnicalTask entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sqlBundle.getString("technicalTask.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        ps.setLong(1, entity.getCustomerId());
        ps.setLong(2, entity.getManagerId());
        ps.setString(3, entity.getName());
        ps.setString(4, entity.getDescription());

        return ps;
    }
}
