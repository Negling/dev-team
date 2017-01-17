package ua.devteam.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.CheckDAO;
import ua.devteam.entity.enums.CheckStatus;
import ua.devteam.entity.users.Check;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Maps {@link Check} entity to table named "checks".
 * <p>
 * Fields which belongs to table is:
 * <p>
 * projectId(project_id),
 * developersCost(developers_cost),
 * taxes(taxes),
 * servicesCost(services),
 * status(status).
 */
@Repository("checkDAO")
public class JDBCCheckDAO extends JDBCGenericDAO<Check> implements CheckDAO {

    @Autowired
    public JDBCCheckDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }

    @Override
    public void create(Check check) {
        jdbcOperations.update(sqlBundle.getString("check.insertSQL"),
                check.getProjectId(),
                check.getDevelopersCost(),
                check.getServicesCost(),
                check.getTaxes(),
                check.getStatus().toString());
    }

    @Override
    public void update(Check oldEntity, Check newEntity) {
        jdbcOperations.update(sqlBundle.getString("check.update"),
                newEntity.getProjectId(),
                newEntity.getDevelopersCost(),
                newEntity.getServicesCost(),
                newEntity.getTaxes(),
                newEntity.getStatus().toString(),
                oldEntity.getProjectId());
    }

    @Override
    public void delete(Check entity) {
        jdbcOperations.update(sqlBundle.getString("check.delete"), entity.getProjectId());
    }

    @Override
    public Check getByProject(Long projectId) {
        return jdbcOperations.queryForObject(sqlBundle.getString("check.selectByProject"), this::mapEntity, projectId);
    }

    @Override
    public List<Check> getNewByCustomer(Long customerId) {
        return jdbcOperations.query(sqlBundle.getString("check.selectNewByCustomer"), this::mapEntity, customerId);
    }

    @Override
    public List<Check> getCompleteByCustomer(Long customerId) {
        return jdbcOperations.query(sqlBundle.getString("check.selectCompleteByCustomer"), this::mapEntity, customerId);
    }

    @Override
    public List<Check> getAllByCustomer(Long customerId) {
        return jdbcOperations.query(sqlBundle.getString("check.selectByCustomer"), this::mapEntity, customerId);
    }

    @Override
    protected Check mapEntity(ResultSet rs, int row) throws SQLException {
        return new Check(rs.getLong("project_id"),
                rs.getString("name"),
                rs.getBigDecimal("developers_cost"),
                rs.getBigDecimal("services"),
                rs.getBigDecimal("taxes"),
                CheckStatus.valueOf(rs.getString("status")));
    }
}
