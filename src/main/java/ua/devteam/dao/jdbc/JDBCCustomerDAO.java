package ua.devteam.dao.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.CustomerDAO;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.Customer;

import java.sql.*;
import java.util.ResourceBundle;

@Repository("customerDAO")
public class JDBCCustomerDAO extends JDBCGenericIdentifiedDAO<Customer> implements CustomerDAO {

    @Autowired
    public JDBCCustomerDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }

    /**
     * Creates record in database for all customer fields, except totalProjectsCost, and returns generated ID.
     *
     * @param customer customer data
     * @return generated ID as long value
     */
    @Override
    public long create(Customer customer) {
        return super.create(customer);
    }

    /**
     * Update  overrides all fields except totalProjectsCost, because this field is calculated by aggregate function.
     * To change that value - update all customer projects individually.
     *
     * @param oldData - new entity data
     * @param newData - old data, only id is necessary.
     */
    @Override
    public void update(Customer oldData, Customer newData) {
        super.jdbcUpdate(sqlBundle.getString("customer.update"),
                newData.getId(),
                newData.getFirstName(),
                newData.getLastName(),
                newData.getUsername(),
                newData.getPhoneNumber(),
                newData.getPassword(),
                newData.getRole().getId(),
                oldData.getId());
    }

    /**
     * Deletes customer from database, to perform operation - only customer ID is necessary.
     *
     * @param customer - customer data
     */
    @Override
    public void delete(Customer customer) {
        super.jdbcUpdate(sqlBundle.getString("customer.delete"), customer.getId());
    }

    @Override
    public Customer getById(Long id) {
        return jdbcOperations.queryForObject(sqlBundle.getString("customer.selectById"), this::mapEntity, id);
    }

    @Override
    public Customer getByEmail(String email) {
        return jdbcOperations.queryForObject(sqlBundle.getString("customer.selectByEmail"), this::mapEntity, email);
    }

    @Override
    protected Customer mapEntity(ResultSet rs, int row) throws SQLException {
        return new Customer(rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("password"),
                Role.valueOf(rs.getString("role")));
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, Customer entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sqlBundle.getString("customer.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setString(3, entity.getEmail());
        ps.setString(4, entity.getPassword());
        ps.setString(5, entity.getPhoneNumber());
        ps.setInt(6, entity.getRole().getId());

        return ps;
    }
}
