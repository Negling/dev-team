package ua.devteam.dao.jdbc;


import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.devteam.dao.Identified;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * For JDBC DAO objects which work with entities with unique id's its necessary to specify own procedure of recording
 * into repository, to return generated ID.
 *
 * @param <T>
 */
abstract class JDBCGenericIdentifiedDAO<T> extends JDBCGenericDAO<T> implements Identified<T> {

    JDBCGenericIdentifiedDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }

    @Override
    public long create(T entity) {
        final KeyHolder key = new GeneratedKeyHolder();

        jdbcOperations.update(con -> insertionStatement(con, entity), key);

        return key.getKey().longValue();
    }

    protected abstract PreparedStatement insertionStatement(Connection connection, T entity) throws SQLException;
}
