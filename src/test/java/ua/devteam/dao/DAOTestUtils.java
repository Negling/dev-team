package ua.devteam.dao;


import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

abstract class DAOTestUtils {

    static <D extends GenericDAO<T> & Identified<T>, T> long createEntityWithIdTest(D entityDAO, T data,
                                                                                    JdbcTemplate jdbcTemplate,
                                                                                    String tableName) {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);

        long id = entityDAO.create(data);

        assertTrue("Id is less than 1", id > 0);
        assertThat(++beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));

        return id;
    }

    static <D extends GenericDAO<T>, T> void updateEntityTest(D entityDAO, T oldData, T newData,
                                                              JdbcTemplate jdbcTemplate, String tableName) {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);

        entityDAO.update(oldData, newData);

        assertThat(beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));
    }

    static <D extends GenericDAO<T>, T> void deleteEntityTest(D entityDAO, T data, JdbcTemplate jdbcTemplate,
                                                              String tableName) {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);

        entityDAO.delete(data);

        assertThat(--beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));
    }

    static <D extends GenericDAO<T> & Identified<T>, T> T getEntityByIdTest(D entityDAO, long id) {
        T data = entityDAO.getById(id);

        assertThat(data, is(notNullValue()));

        return data;
    }
}
