package sample.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import sample.bean.Event;
import sample.bean.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static sample.connection.constants.Const.*;

@SuppressWarnings("Duplicates")
public class DBHandler extends Config{

    public static final DBHandler INSTANCE = new DBHandler();

    private static final Logger LOGGER = (Logger) LogManager.getLogger(DBHandler.class);
    private Connection dbConnection;
    private static final int ADMIN_ROLE = 2;
    private static final String INSERT_INTO_USERS_TABLE = "INSERT INTO " + USER_TABLE + "("
            + USERS_NAME + ","
            + USERS_SURNAME + ","
            + USERS_LOGIN + ","
            + USERS_PASSWORD + ","
            + USERS_ROLE + ")"
            + "VALUES(?,?,?,?,?)";
    private static final String SELECT_FROM_USERS_TABLE = "SELECT * FROM " + USER_TABLE + " WHERE " + USERS_LOGIN
            + "=? AND " + USERS_PASSWORD + "=?";
    private static final String CHECK_ADMIN_RIGHTS_SELECT = "SELECT role FROM " + USER_TABLE + " WHERE " + USERS_LOGIN
            + "=?";
    private final String CONNECTION = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String CHECK_LOGIN_EXISTING = "SELECT login FROM " + USER_TABLE + " WHERE " + USERS_LOGIN
            + "=?";
    private static final String SELECT_EVENT = "SELECT * FROM " + EVENT_TABLE;
    private static final String SELECT_CUSTOMERS = "SELECT * FROM " + CUSTOMERS_TABLE;
    private static final String SELECT_EVENT_VIEW = "SELECT * FROM " + EVENT_VIEW;

    private DBHandler(){
    }

    private Connection getDbConnection() throws ClassNotFoundException, SQLException{
        Class.forName(JDBC_DRIVER);
        dbConnection = DriverManager.getConnection(CONNECTION, dbUser, dbPassword);
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
            LOGGER.error("signUp method exception");
        }
    }

    public void signUpUser(User user){
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(INSERT_INTO_USERS_TABLE);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, String.valueOf(user.getRole()));
            preparedStatement.executeUpdate();
        } catch(ClassNotFoundException | SQLException e){
            LOGGER.error("signUp method exception");
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
            LOGGER.error("signInUser method select sqlException");
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
            LOGGER.error("checkAdminRights Exception");
        }

        return Integer.valueOf(roleList.get(0)) == ADMIN_ROLE;
    }

    public boolean checkLoginExisting(String login){
        ResultSet resultSet;
        List<String> loginList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(CHECK_LOGIN_EXISTING);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                loginList.add(resultSet.getString(1));
            }
        } catch(ClassNotFoundException | SQLException e){
            LOGGER.error("checkLogin Exception");
        }
//        System.out.println(loginList.get(0));
        if(loginList.isEmpty()){
            return false;
        } else {
            return loginList.get(0).equals(login);
        }
    }

    public ResultSet getEventData(){
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(SELECT_EVENT_VIEW);
            resultSet = preparedStatement.executeQuery();
        } catch(ClassNotFoundException | SQLException e) {
            LOGGER.error("checkLogin Exception");
        }
        return resultSet;
    }

    public ResultSet getCustomerData() throws SQLException, ClassNotFoundException {
        ResultSet resultSet;
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(SELECT_CUSTOMERS);
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
}
