package ua.devteam.dao.jdbc;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcOperations;
import ua.devteam.dao.GenericDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

abstract class JDBCGenericDAO<T> implements GenericDAO<T> {

    protected JdbcOperations jdbcOperations;
    protected ResourceBundle sqlBundle;

    JDBCGenericDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        this.jdbcOperations = jdbcOperations;
        this.sqlBundle = sqlBundle;
    }

    protected void jdbcUpdate(String sql, Object... args) {
        if (jdbcOperations.update(sql, args) < 1)
            throw new DataIntegrityViolationException("No rows were changed during update!");
    }

    protected abstract T mapEntity(ResultSet rs, int row) throws SQLException;
}
