package ua.devteam.dao.jdbc;

import org.springframework.jdbc.core.JdbcOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * To work with repository - all JDBC DAO objects must have access to sql queries message bundle,
 * and spring jdbcOperations object.
 *
 * @param <T>
 */
abstract class JDBCGenericDAO<T> {

    protected JdbcOperations jdbcOperations;
    protected ResourceBundle sqlProperties;

    JDBCGenericDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        this.jdbcOperations = jdbcOperations;
        this.sqlProperties = sqlProperties;
    }


    protected abstract T mapEntity(ResultSet rs, int row) throws SQLException;
}
