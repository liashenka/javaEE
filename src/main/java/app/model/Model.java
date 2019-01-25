package app.model;

import app.entities.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.Connection;

public class Model {
    private static Model instance = new Model();
    private String userName = "root";
    private String passDB = "111";
    private String connectionUrl = "jdbc:mysql://localhost:3306/webapp";



    private List<User> model;

    public static Model getInstance() {
        return instance;
    }

    private Model() {
        model = new ArrayList<>();
    }

    public void addUserIntoDB(String newUserName, String newUserPassword) throws ClassNotFoundException, SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO adduser (name, password) values (?, ?)";
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, passDB);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newUserName);
            preparedStatement.setString(2, newUserPassword);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> list() throws ClassNotFoundException, SQLException {
        List<String> list = new ArrayList<>();
        Class.forName("com.mysql.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl, userName, passDB)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from adduser");

            while (resultSet.next()){
                    list.add(resultSet.getString(1));
            }
        }

        return list;
    }
}
