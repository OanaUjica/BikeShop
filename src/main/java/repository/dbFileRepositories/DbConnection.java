package repository.dbFileRepositories;

import java.sql.*;

public class DbConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/BikeShop";
    private static final String USER = "postgres";
    private static final String PASSWORD = "enter123";

    public ResultSet getResultSet(String sql) {

        try {

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet;

        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }

        return null;
    }

    public PreparedStatement getPreparedStatement(String sql) {

        try {

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            return preparedStatement;

        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }

        return null;
    }
}
