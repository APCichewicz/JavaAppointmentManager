package com.example.wgusoftware2scheduler.Utility;
/**

 Class for storing the current user ID.
 */
public class CurrentUser {

    private static int userID;
    private static CurrentUser instance = null;
    /**

     Private constructor to enforce singleton pattern.
     */
    private CurrentUser() {
    }
    /**

     Gets the singleton instance of this class.
     @return the singleton instance of this class
     */
    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }
    /**

     Gets the ID of the current user.
     @return the ID of the current user
     */
    public static int getUserID() {
        return userID;
    }
    /**

     Sets the ID of the current user.
     @param userID the ID of the current user
     */
    public static void setUserID(int userID) {
        CurrentUser.userID = userID;
    }

}
