package com.example.wgusoftware2scheduler.Controllers;
import com.example.wgusoftware2scheduler.Models.Appointment;
import com.example.wgusoftware2scheduler.Models.DataSet;
import com.example.wgusoftware2scheduler.Utility.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Locale;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
/**

 LoginController is a class that handles the functionality for the login screen of the application.

 It handles user authentication, displays local time, and provides user feedback on login attempts.
 */
import com.example.wgusoftware2scheduler.Utility.JDBC;

public class LoginController {
    @FXML
    public Label localTime;
    @FXML
    public PasswordField PasswordField;
    @FXML
    private TextField UserNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button LoginSubmitButton;
    @FXML
    private Button LoginExitButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;

    private String error;
    private String incorrectUsernamePassword;
    /**

     Initializes the login screen with localized text and sets the local time.
     */
    @FXML
    public void initialize() {
        String language = Locale.getDefault().getDisplayLanguage();
        this.error = (language.equals("English")) ? "Error" : "Erreur";
        usernameLabel.setText(((language.equals("English")) ? "Username" : "Nom d'utilisateur"));
        passwordLabel.setText((language.equals("English")) ? "Password" : "Mot de passe");
        LoginSubmitButton.setText((language.equals("English")) ? "Submit" : "Soumettre");
        LoginExitButton.setText((language.equals("English")) ? "Exit" : "Sortie");
        this.incorrectUsernamePassword = (language.equals("English")) ? "Incorrect Username or Password"
                : "Nom d'utilisateur ou mot de passe incorrect";
        localTime.setText(java.time.ZoneId.systemDefault().toString());
    }
    /**

     Handles the login submit button action by authenticating the user and, if successful, changing the scene to the main view.
     Also displays appointment reminders if there is an appointment within 15 minutes.
     @param event the ActionEvent triggered by the login submit button
     */
    @FXML
    private void LoginSubmitButtonAction(ActionEvent event) {
        boolean authenticated = false;

        if (UserNameField.getText().trim().isEmpty() || PasswordField.getText().trim().isEmpty()) {
            Popups.error(error, incorrectUsernamePassword);
        }
        String username = UserNameField.getText();
        String password = PasswordField.getText();
            authenticated = JDBC.authenticate(username, password);
        if (authenticated) {
            DataSet.getInstance();
            CurrentUser.setUserID(DataSet.getUser(username).getID());

            Optional<Appointment> appointment = DataSet.getAppointments().stream()
                    .filter(app -> app.getUserID() == CurrentUser.getUserID())
                    .filter(a -> TimeHandler.isWithinFifteenMinutes(a.getStartTime()))
                    .findFirst();

            if (appointment.isPresent()) {
                Popups.alert("Appointment Reminder", "You have an appointment in 15 minutes\n" + appointment.get().toString());
            } else {
                Popups.alert("No Appointments", "There are no appointments within the next 15 minutes.");
            }

            Scene scene = ((Button) event.getSource()).getScene();

            Stage stage = (Stage) scene.getWindow();

            try {
                stage.setScene(new Scene(FXMLLoader
                        .load(getClass().getResource("/com/example/wgusoftware2scheduler/resources/MainView.fxml"))));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {Popups.error(error, incorrectUsernamePassword);}




        writeLoginAttempt(authenticated);
    }
    /**

     Handles the login exit button action by closing the application.
     @param event the ActionEvent triggered by the login exit button
     */
    @FXML
    private void LoginExitButtonAction(ActionEvent event) {

        Scene scene = ((Button) event.getSource()).getScene();

        Stage stage = (Stage) scene.getWindow();

        stage.close();
    }
    /**

     Writes the login attempt, whether successful or not, to a file named "login_activity.txt".
     @param success a boolean indicating whether the login attempt was successful
     */
    private void writeLoginAttempt(boolean success) {

        String time = java.time.LocalDateTime.now().toString();

        String line = "login attempt at: "
                + TimeHandler.getNowLocal().toString() + " - " + (success ? "success" : "failure");

        File file = new File("login_activity.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

            writer.write(line);

            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
