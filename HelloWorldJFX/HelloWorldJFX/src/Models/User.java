package Models;

/**
 * User class containing info to set up the User and all its functions
 **/

public class User {

    public int userID;
    public String userName;
    public String userPassword;

    public User(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * @return userID
     */
    public int getUserID() {

        return userID;
    }
//
//    /**
//     * @return userName
//     */
//    public String getUserName() {
//
//        return userName;
//    }
//
//    /**
//     * @return userPassword
//     */
//    public String getUserPassword() {
//
//        return userPassword;
//    }

    @Override
    public String toString()
    {
        return userName;
    }

}