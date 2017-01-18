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
@Repository("customerDAO")
public class JDBCCustomerDAO extends JDBCGenericIdentifiedDAO<Customer> implements CustomerDAO {

    @Autowired
    public JDBCCustomerDAO(JdbcOperations jdbcOperations, ResourceBundle sqlBundle) {
        super(jdbcOperations, sqlBundle);
    }


    @Override
    public long create(Customer customer) {
        return super.create(customer);
    }


    @Override
    public void update(Customer oldData, Customer newData) {
        jdbcOperations.update(sqlBundle.getString("customer.update"),
                newData.getId(),
                newData.getFirstName(),
                newData.getLastName(),
                newData.getUsername(),
                newData.getPhoneNumber(),
                newData.getPassword(),
                newData.getRole().getId(),
                oldData.getId());
    }


    @Override
    public void delete(Customer customer) {
        jdbcOperations.update(sqlBundle.getString("customer.delete"), customer.getId());
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
        ps.setString(4, entity.getPhoneNumber());
        ps.setString(5, entity.getPassword());
        ps.setInt(6, entity.getRole().getId());

        return ps;
    }
}
