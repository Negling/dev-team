package ua.devteam.dao.jdbc;

import org.springframework.jdbc.core.JdbcOperations;
import ua.devteam.dao.GenericDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * To work with repository - all JDBC DAO objects must have access to sql queries message bundle,
 * and spring jdbcOperations object.
 *
 * @param <T>
 */
abstract class JDBCGenericDAO<T> implements GenericDAO<T> {

    protected JdbcOperations jdbcOperations;
    protected ResourceBundle sqlBundle;

    JDBCGenericDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        this.jdbcOperations = jdbcOperations;
        this.sqlBundle = sqlBundle;
    }


    protected abstract T mapEntity(ResultSet rs, int row) throws SQLException;
}
