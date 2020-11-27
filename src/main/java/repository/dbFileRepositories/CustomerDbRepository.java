package repository.dbFileRepositories;

import model.Customer;
import model.validators.Validator;
import repository.IRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDbRepository implements IRepository<Long, Customer> {

    private DbConnection dbConnection;
    private Validator<Customer> customerValidator;

    public CustomerDbRepository(Validator<Customer> customerValidator) {

        this.dbConnection = new DbConnection();
        this.customerValidator = customerValidator;
    }

    @Override
    public Optional<Customer> findOne(Long id) {

        if (id == null) {

            throw new IllegalArgumentException("id must not be null");
        }

        Customer customer = null;

        String sql = "select * from customers where c_id = ?";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String number = resultSet.getString("number");

                customer = new Customer(id, firstName, lastName, phone, city, street, number);
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return Optional.ofNullable(customer);
    }

    @Override
    public Iterable<Customer> findAll() {

        List<Customer> customers = new ArrayList<>();
        String sql = "select * from customers";

        try (ResultSet resultSet = dbConnection.getResultSet(sql)) {

            while (resultSet.next()) {

                Long id = resultSet.getLong("c_id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String number = resultSet.getString("number");

                Customer customer = new Customer(id, firstName, lastName, phone, city, street, number);
                customers.add(customer);
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return new ArrayList<>(customers);
    }

    @Override
    public Optional<Customer> save(Customer customer) {

        if (customer == null) {

            throw new IllegalArgumentException("customer must not be null");
        }
        this.customerValidator.validate(customer);

        String sql = "insert into customers(firstname, lastname, phone, city, street, number) values(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setString(4, customer.getCity());
            preparedStatement.setString(5, customer.getStreet());
            preparedStatement.setString(6, customer.getNumber());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return Optional.of(customer);
    }

    @Override
    public Optional<Customer> delete(Long id) {

        if (id == null) {

            throw new IllegalArgumentException("id must not be null!");
        }

        String sql = "delete from customers where c_id = ?";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return findOne(id);
    }

    @Override
    public Optional<Customer> update(Customer customer) {

        if (customer == null) {

            throw new IllegalArgumentException("customer must not be null");
        }

        String sql = "update customers set firstname = ?, lastname = ?, phone = ?, city = ?, street = ?, number = ? where c_id = ?";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setString(4, customer.getCity());
            preparedStatement.setString(5, customer.getStreet());
            preparedStatement.setString(6, customer.getNumber());
            preparedStatement.setLong(7, customer.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return Optional.of(customer);
    }
}
