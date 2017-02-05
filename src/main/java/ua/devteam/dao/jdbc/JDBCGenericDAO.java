package ua.devteam.dao.jdbc;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@AllArgsConstructor
abstract class JDBCGenericDAO<T> {

    private @Getter(AccessLevel.PROTECTED) JdbcOperations jdbcOperations;
    private @Getter(AccessLevel.PROTECTED) ResourceBundle sqlProperties;


    protected abstract T mapEntity(ResultSet rs, int row) throws SQLException;
}
