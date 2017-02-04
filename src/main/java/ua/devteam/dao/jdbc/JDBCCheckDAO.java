package ua.devteam.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.CheckDAO;
import ua.devteam.entity.enums.CheckStatus;
import ua.devteam.entity.projects.Check;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Maps {@link Check} entity to table named "checks".
 * <p>
 * Fields which belongs to table is:
 * <p>
 * <ul>
 * <li> projectId(project_id),
 * <li> developersCost(developers_cost),
 * <li> taxes(taxes),
 * <li> servicesCost(services),
 * <li> status(status).
 * </ul>
 */
@Repository
public class JDBCCheckDAO extends JDBCGenericDAO<Check> implements CheckDAO {

    @Autowired
    public JDBCCheckDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }

    @Override
    public void create(Check check) {
        jdbcOperations.update(sqlProperties.getString("check.insertSQL"),
                check.getProjectId(),
                check.getCustomerId(),
                check.getDevelopersCost(),
                check.getServicesCost(),
                check.getTaxes(),
                check.getStatus().toString());
    }

    @Override
    public void update(Check oldEntity, Check newEntity) {
        jdbcOperations.update(sqlProperties.getString("check.update"),
                newEntity.getProjectId(),
                newEntity.getCustomerId(),
                newEntity.getDevelopersCost(),
                newEntity.getServicesCost(),
                newEntity.getTaxes(),
                newEntity.getStatus().toString(),
                oldEntity.getProjectId(),
                oldEntity.getCustomerId());
    }

    @Override
    public void delete(Check entity) {
        jdbcOperations.update(sqlProperties.getString("check.deleteByProject"), entity.getProjectId());
    }

    @Override
    public Check getByProject(Long projectId) {
        return jdbcOperations.queryForObject(sqlProperties.getString("check.selectByProject"), this::mapEntity, projectId);
    }

    @Override
    public List<Check> getNewByCustomer(Long customerId) {
        return jdbcOperations.query(sqlProperties.getString("check.selectNewByCustomer"), this::mapEntity, customerId);
    }

    @Override
    public List<Check> getCompleteByCustomer(Long customerId) {
        return jdbcOperations.query(sqlProperties.getString("check.selectCompleteByCustomer"), this::mapEntity, customerId);
    }

    @Override
    public List<Check> getAllByCustomer(Long customerId) {
        return jdbcOperations.query(sqlProperties.getString("check.selectByCustomer"), this::mapEntity, customerId);
    }

    @Override
    protected Check mapEntity(ResultSet rs, int row) throws SQLException {
        return new
                Check.Builder(rs.getBigDecimal("developers_cost"),
                rs.getBigDecimal("services"),
                rs.getBigDecimal("taxes"),
                CheckStatus.valueOf(rs.getString("status")))
                .setProjectId(rs.getLong("project_id"))
                .setProjectName(rs.getString("name"))
                .setCustomerId(rs.getLong("customer_id")).build();
    }
}
