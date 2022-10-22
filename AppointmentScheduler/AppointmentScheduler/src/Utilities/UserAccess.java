package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.DBConnection;
import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Communicates with sql to send and receive the new information being saved, updated, or deleted
 */

public class UserAccess
{
    /**
     * checks and ensures that user name is valid
     * @param password password
     * @param username username
     */
    public static int validUser(String username, String password)
    {
        try
        {
            String sqlQuery = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password +"'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                if (rs.getString("User_Name").equals(username))
                {
                    if (rs.getString("Password").equals(password))
                    {
                        return rs.getInt("User_ID");
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets the users
     * @throws SQLException Sql exception
     * @return usersObservableList
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> usersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from users";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            User user = new User(userID, userName, userPassword);
            usersObservableList.add(user);
        }
        return usersObservableList;
    }
}
