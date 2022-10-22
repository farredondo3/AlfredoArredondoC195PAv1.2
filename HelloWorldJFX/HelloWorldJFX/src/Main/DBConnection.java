package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Connects the java project to the SQLworkbench
 */
public class DBConnection
{
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//localhost/";
    private static final String databaseName = "client_schedule";



    private static final String jdbcURL = protocol + vendorName + ipAddress + databaseName + "?connectionTimeZone = SERVER";

    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    private static Connection connect = null;

    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";

    /**
     * Connects to the database.
     * @return conn
     */
    public static Connection startConnection()
    {
        try {
            Class.forName(MYSQLJDBCDriver);
            connect = (Connection) DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");
        } catch (ClassNotFoundException | SQLException e) {

        }
        return connect;
    }

    /**
     * Gets current connection.
     * @return current connection.
     */
    public static Connection getConnection() {

        return connect;
    }

    /**
     * Closes the connection.
     */
    public static void closeConnection() {
        try {
            connect.close();
            System.out.println("Connection closed");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets prepared statement
     * @param sqlStatement Sql statement
     * @return Prepared statement
     * @throws SQLException Sql exception
     */

    public static PreparedStatement getPreparedStatement(String sqlStatement) throws SQLException {

        return connect.prepareStatement(sqlStatement);
    }
}
