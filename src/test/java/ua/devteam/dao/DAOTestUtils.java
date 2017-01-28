package ua.devteam.dao;


import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

/**
 * Util class to provide some common asserts within DAO layer integration tests.
 */
abstract class DAOTestUtils {

    /**
     * Creates data in with dao instance, and checks that was inserted exactly one row in selected table and generated ID is not null.
     *
     * @param entityDAO    dao entity which provides operation
     * @param data         to create
     * @param jdbcTemplate instance
     * @param tableName    name of operating table
     * @param <D>          dao instance param
     * @param <T>          data instance param
     * @return id of created data
     */
    static <D extends GenericDAO<T> & Identified<T>, T> long createEntityWithIdTest(D entityDAO, T data,
                                                                                    JdbcTemplate jdbcTemplate,
                                                                                    String tableName) {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);

        long id = entityDAO.create(data);

        assertTrue("Id is less than 1", id > 0);
        assertThat(++beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));

        return id;
    }

    /**
     * Updates old data by new data with dao instance, and checks that rows in table is equal to its count before update.
     *
     * @param entityDAO    dao entity which provides operation
     * @param oldData      data to be updated
     * @param newData      new data
     * @param jdbcTemplate instance
     * @param tableName    name of operating table
     * @param <D>          dao instance param
     * @param <T>          data instance param
     */
    static <D extends GenericDAO<T>, T> void updateEntityTest(D entityDAO, T oldData, T newData,
                                                              JdbcTemplate jdbcTemplate, String tableName) {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);

        entityDAO.update(oldData, newData);

        assertThat(beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));
    }

    /**
     * Deletes data with dao instance, and checks that in table was deleted exactly one row.
     *
     * @param entityDAO    dao entity which provides operation
     * @param data         to create
     * @param jdbcTemplate instance
     * @param tableName    name of operating table
     * @param <D>          dao instance param
     * @param <T>          data instance param
     */
    static <D extends GenericDAO<T>, T> void deleteEntityTest(D entityDAO, T data, JdbcTemplate jdbcTemplate,
                                                              String tableName) {
        int beforeOperation = countRowsInTable(jdbcTemplate, tableName);

        entityDAO.delete(data);

        assertThat(--beforeOperation, is(countRowsInTable(jdbcTemplate, tableName)));
    }

    /**
     * Retrieves data from table with dao instance, and checks that retrieved data is not null value.
     *
     * @param entityDAO dao entity which provides operation
     * @param id        param
     * @param <D>       dao instance param
     * @param <T>       data instance param
     * @return data object
     */
    static <D extends GenericDAO<T> & Identified<T>, T> T getEntityByIdTest(D entityDAO, long id) {
        T data = entityDAO.getById(id);

        assertThat(data, is(notNullValue()));

        return data;
    }
}
