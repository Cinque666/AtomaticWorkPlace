package sample.connection;

import sample.bean.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static sample.connection.constants.Const.*;

public class DBHandler extends Config{

    public static final DBHandler INSTANCE = new DBHandler();

    private Connection dbConnection;
    private static final int ADMIN_ROLE = 2;
    private static final String INSERT_INTO_USERS_TABLE = "INSERT INTO " + USER_TABLE + "("
            + USERS_NAME + ","
            + USERS_SURNAME + ","
            + USERS_LOGIN + ","
            + USERS_PASSWORD + ")"
            + "VALUES(?,?,?,?)";
    private static final String SELECT_FROM_USERS_TABLE = "SELECT * FROM " + USER_TABLE + " WHERE " + USERS_LOGIN
            + "=? AND " + USERS_PASSWORD + "=?";
    private static final String CHECK_ADMIN_RIGHTS_SELECT = "SELECT role FROM " + USER_TABLE + " WHERE " + USERS_LOGIN
            + "=?";

    private DBHandler(){
    }

    private Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connection = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
                + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connection, dbUser, dbPassword);
        return dbConnection;
    }

    /**
     * deprecated
     */
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

    public boolean checkAdminRights(String username){
        ResultSet resultSet;
        List<String> roleList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(CHECK_ADMIN_RIGHTS_SELECT);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                roleList.add(resultSet.getString(1));
            }
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

        return Integer.valueOf(roleList.get(0)) == ADMIN_ROLE;
    }
}
