package sample.connection;

import sample.bean.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import static sample.connection.constants.Const.*;

public class DBHandler extends Config{

    private Connection dbConnection;
    private static final String INSERT_INTO_USERS_TABLE = "INSERT INTO " + USER_TABLE + "("
            + USERS_NAME + ","
            + USERS_SURNAME + ","
            + USERS_LOGIN + ","
            + USERS_PASSWORD + ")"
            + "VALUES(?,?,?,?)";
    private static final String SELECT_FROM_USERS_TABLE = "SELECT * FROM " + USER_TABLE + " WHERE " + USERS_LOGIN
            + "=? AND " + USERS_PASSWORD + "=?";

    private Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connection = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
                + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connection, dbUser, dbPassword);
        return dbConnection;
    }

    public void signUpUser(String name, String surname, String login, String password){
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(INSERT_INTO_USERS_TABLE);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public void signUpUser(User user){
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(INSERT_INTO_USERS_TABLE);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet signInUser(User user){
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(SELECT_FROM_USERS_TABLE);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery(); //select
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }
}
