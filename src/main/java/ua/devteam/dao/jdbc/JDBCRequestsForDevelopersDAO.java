package ua.devteam.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.RequestsForDevelopersDAO;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.tasks.RequestForDevelopers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Maps {@link RequestForDevelopers} entity to table named "requests_for_developers".
 * <p>
 * Fields which belongs to table is:
 * <p><ul>
 * <li> operationId(operation_id),
 * <li> specialization(specialization),
 * <li> rank(rank),
 * <li> quantity(quantity).
 * </ul>
 */
@Repository
public class JDBCRequestsForDevelopersDAO extends JDBCGenericDAO<RequestForDevelopers> implements RequestsForDevelopersDAO {

    @Autowired
    public JDBCRequestsForDevelopersDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }

    @Override
    public void create(RequestForDevelopers requestForDevelopers) {
        getJdbcOperations().update(getSqlProperties().getString("developersRequest.insertSQL"),
                requestForDevelopers.getOperationId(),
                requestForDevelopers.getSpecialization().toString(),
                requestForDevelopers.getRank().toString(),
                requestForDevelopers.getQuantity());
    }

    @Override
    public void update(RequestForDevelopers oldEntity, RequestForDevelopers newEntity) {
        getJdbcOperations().update(getSqlProperties().getString("developersRequest.update"),
                newEntity.getOperationId(),
                newEntity.getSpecialization().toString(),
                newEntity.getRank().toString(),
                newEntity.getQuantity(),
                oldEntity.getOperationId(),
                oldEntity.getSpecialization().toString(),
                oldEntity.getRank().toString(),
                oldEntity.getQuantity());
    }

    @Override
    public void delete(RequestForDevelopers entity) {
        getJdbcOperations().update(getSqlProperties().getString("developersRequest.delete"),
                entity.getOperationId(),
                entity.getSpecialization().toString(),
                entity.getRank().toString(),
                entity.getQuantity());
    }

    @Override
    public List<RequestForDevelopers> getByOperation(Long operationId) {
        return getJdbcOperations().query(getSqlProperties().getString("developersRequest.selectByOperation"),
                this::mapEntity, operationId);
    }

    @Override
    protected RequestForDevelopers mapEntity(ResultSet rs, int row) throws SQLException {

        return new RequestForDevelopers(rs.getLong("operation_id"),
                DeveloperSpecialization.valueOf(rs.getString("specialization")),
                DeveloperRank.valueOf(rs.getString("rank")),
                rs.getInt("quantity"));
    }
}
