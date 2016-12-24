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

@Repository("requestsForDevelopersDAO")
public class JDBCRequestsForDevelopersDAO extends JDBCGenericDAO<RequestForDevelopers> implements RequestsForDevelopersDAO {

    @Autowired
    public JDBCRequestsForDevelopersDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }

    @Override
    public void create(RequestForDevelopers entity) {
        super.jdbcUpdate(sqlBundle.getString("developersRequest.insertSQL"),
                entity.getOperation_id(),
                entity.getSpecialization().toString(),
                entity.getRank().toString(),
                entity.getQuantity());
    }

    @Override
    public void update(RequestForDevelopers oldEntity, RequestForDevelopers newEntity) {
        super.jdbcUpdate(sqlBundle.getString("developersRequest.update"),
                newEntity.getOperation_id(),
                newEntity.getSpecialization().toString(),
                newEntity.getRank().toString(),
                newEntity.getQuantity(),
                oldEntity.getOperation_id(),
                oldEntity.getSpecialization().toString(),
                oldEntity.getRank().toString(),
                oldEntity.getQuantity());
    }

    @Override
    public void delete(RequestForDevelopers entity) {
        super.jdbcUpdate(sqlBundle.getString("developersRequest.delete"),
                entity.getOperation_id(),
                entity.getSpecialization().toString(),
                entity.getRank().toString(),
                entity.getQuantity());
    }

    @Override
    public List<RequestForDevelopers> getByOperation(Long taskId) {
        return jdbcOperations.query(sqlBundle.getString("developersRequest.selectByOperation"), this::mapEntity, taskId);
    }

    @Override
    protected RequestForDevelopers mapEntity(ResultSet rs, int row) throws SQLException {

        return new RequestForDevelopers(rs.getLong("operation_id"),
                DeveloperSpecialization.valueOf(rs.getString("specialization")),
                DeveloperRank.valueOf(rs.getString("rank")),
                rs.getInt("quantity"));
    }
}
