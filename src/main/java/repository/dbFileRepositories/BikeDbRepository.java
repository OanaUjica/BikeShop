package repository.dbFileRepositories;

import model.Bike;
import model.BikeType;
import model.validators.BikeShopException;
import model.validators.BikeValidator;
import model.validators.Validator;
import repository.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BikeDbRepository implements IRepository<Long, Bike> {

    private DbConnection dbConnection;
    private Validator<Bike> bikeValidator;

    public BikeDbRepository(Validator<Bike> bikeValidator) {

        this.dbConnection = new DbConnection();
        this.bikeValidator = bikeValidator;
    }

    @Override
    public Optional<Bike> findOne(Long id) {

        String sql = "select * from bikes where id = ?";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                String name = resultSet.getString("name");
                BikeType bikeType = BikeType.valueOf(resultSet.getString("biketype").toUpperCase());
                double price = resultSet.getDouble("price");

                Bike bike = new Bike(id, name, bikeType, price);

                return Optional.of(bike);
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Iterable<Bike> findAll() {

        List<Bike> bikes = new ArrayList<>();
        String sql = "select * from bikes";

        try (ResultSet resultSet = dbConnection.getResultSet(sql)) {

            while (resultSet.next()) {

                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BikeType bikeType = BikeType.valueOf(resultSet.getString("biketype").toUpperCase());
                double price = resultSet.getDouble("price");

                Bike bike = new Bike(id, name, bikeType, price);
                bikes.add(bike);
            }

        } catch (SQLException ex) {

            throw new BikeShopException("There are no records in the table");
        }

        return new ArrayList<>(bikes);
    }

    @Override
    public Optional<Bike> save(Bike bike) {

        if (bike == null) {

            throw new IllegalArgumentException("bike must not be null");
        }

        bikeValidator.validate(bike);
        String sql = "insert into bikes (name, biketype, price) values (?, ?, ?)";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setString(1, bike.getName());
            preparedStatement.setString(2, bike.getType().getBikeType().toLowerCase());
            preparedStatement.setDouble(3, bike.getPrice());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return Optional.of(bike);
    }

    @Override
    public Optional<Bike> delete(Long id) {

        String sql = "delete from bikes where id = ?";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return findOne(id);
    }

    @Override
    public Optional<Bike> update(Bike bike) {

        String sql = "update bikes set name = ?, biketype = ?, price = ? where id = ?";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setString(1, bike.getName());
            preparedStatement.setString(2, bike.getType().getBikeType().toLowerCase());
            preparedStatement.setDouble(3, bike.getPrice());
            preparedStatement.setLong(4, bike.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return Optional.of(bike);
    }
}
