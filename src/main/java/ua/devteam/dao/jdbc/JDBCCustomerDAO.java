package ua.devteam.dao.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ua.devteam.dao.CustomerDAO;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.Customer;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * Maps {@link Customer} entity to table named "customers".
 * <p>
 * Fields which belongs to table is:
 * <p><ul>
 * <li> id(id),
 * <li> firstName(first_name),
 * <li> lastName(last_name),
 * <li> email(email),
 * <li> phoneNumber(phone),
 * <li> password(password),
 * <li> role(role_id).
 * </ul>
 */
@Repository
public class JDBCCustomerDAO extends JDBCGenericIdentifiedDAO<Customer> implements CustomerDAO {

    @Autowired
    public JDBCCustomerDAO(JdbcOperations jdbcOperations, ResourceBundle sqlProperties) {
        super(jdbcOperations, sqlProperties);
    }


    @Override
    public long create(Customer customer) {
        return super.create(customer);
    }


    @Override
    public void update(Customer oldData, Customer newData) {
        getJdbcOperations().update(getSqlProperties().getString("customer.update"),
                newData.getId(),
                newData.getFirstName(),
                newData.getLastName(),
                newData.getUsername(),
                newData.getPhoneNumber(),
                newData.getPassword(),
                newData.getRole().toString(),
                oldData.getId());
    }


    @Override
    public void delete(Customer customer) {
        getJdbcOperations().update(getSqlProperties().getString("customer.delete"), customer.getId());
    }

    @Override
    public Customer getById(Long id) {
        return getJdbcOperations().queryForObject(getSqlProperties().getString("customer.selectById"), this::mapEntity, id);
    }

    @Override
    public Customer getByEmail(String email) {
        return getJdbcOperations().queryForObject(getSqlProperties().getString("customer.selectByEmail"), this::mapEntity, email);
    }

    @Override
    protected Customer mapEntity(ResultSet rs, int row) throws SQLException {
        return new
                Customer.Builder()
                .setId(rs.getLong("id"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setEmail(rs.getString("email"))
                .setPhoneNumber(rs.getString("phone"))
                .setPassword(rs.getString("password"))
                .setRole(Role.valueOf(rs.getString("role")))
                .build();
    }

    @Override
    protected PreparedStatement insertionStatement(Connection connection, Customer entity) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(getSqlProperties().getString("customer.insertSQL"),
                Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setString(3, entity.getEmail());
        ps.setString(4, entity.getPhoneNumber());
        ps.setString(5, entity.getPassword());
        ps.setString(6, entity.getRole().toString());

        return ps;
    }
}
