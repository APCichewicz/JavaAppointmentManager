package com.example.wgusoftware2scheduler.Utility;
/**

 The Popups class is used to display different types of pop-up messages to the user.
 It provides methods for displaying alerts, confirmation dialogs, and error messages.
 */
public class Popups {
    /*

    This method displays an information alert with the specified title and message.
    @param title The title of the alert.
    @param message The message to be displayed in the alert.
    */
    public static void alert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**

     This method displays a confirmation dialog with the specified title and message.
     @param title The title of the dialog.
     @param message The message to be displayed in the dialog.
     @return A boolean value indicating whether the user clicked OK or not.
     */
    public static boolean confirmDialog(String title, String message){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        java.util.Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            return true;
        }
        return false;
    }
    /**

     This method displays an error alert with the specified title and message.
     @param title The title of the alert.
     @param message The message to be displayed in the alert.
     */
    public static void error(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
