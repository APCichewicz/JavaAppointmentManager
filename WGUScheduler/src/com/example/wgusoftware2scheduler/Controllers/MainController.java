package com.example.wgusoftware2scheduler.Controllers;


import com.example.wgusoftware2scheduler.App;
import com.example.wgusoftware2scheduler.Models.DataSet;
import com.example.wgusoftware2scheduler.Utility.JDBC;
import com.example.wgusoftware2scheduler.Utility.Popups;
import com.example.wgusoftware2scheduler.Utility.TimeHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.Scene;

import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.stream.Collectors;

import javafx.scene.Parent;
import com.example.wgusoftware2scheduler.Models.Appointment;
import com.example.wgusoftware2scheduler.Models.Customer;
/**

 MainController is a class that handles the functionality for the main screen of the application.

 It displays tables for appointments and customers, and allows the user to add, update, or delete appointments and customers.

 The user can also filter the appointments based on week, month, or view all appointments.
 */
public class MainController {

    public TableColumn<Appointment, Integer> AppIdColumn;
    public TableColumn<Appointment, String> AppTitleColumn;
    public TableColumn<Appointment, String> AppTypeColumn;
    public TableColumn<Appointment, String> AppLocationColumn;
    public TableColumn<Appointment, String> AppDescColumn;
    public TableColumn<Appointment, LocalDate> AppDateColumn;
    public TableColumn<Appointment, LocalDate> AppDateColumn1;
    public TableColumn<Appointment, LocalTime> AppStartColumn;
    public TableColumn<Appointment, LocalTime> AppEndColumn;
    public TableColumn<Appointment, Integer> AppContactColumn;
    public TableColumn<Appointment, Integer> AppCustomerColumn;
    public TableColumn<Appointment, Integer> AppUserColumn;
    public Button AppointmentsAddButton;
    public Button AppointmentsUpdateButton;
    public Button AppoinmentsDeleteButton;
    public TableColumn<Customer, Integer> CustIdColumn;
    public TableColumn<Customer, String> CustNameColumn;
    public TableColumn<Customer, String> CustAddressColumn;
    public TableColumn<Customer, String> CustPhoneColumn;
    public TableColumn<Customer, String> CustStateProvinceColumn;
    public TableColumn<Customer, String> CustPostalColumn;
    public Button CustomersAddButton;
    public Button CustomersUpdateButton;
    public Button CustomersDeleteButton;
    public Button LogoutButton;
    public Button ReportsButton;
    public RadioButton week;
    public RadioButton Month;
    public RadioButton All;
    public TableView AppTable;
    public TableView CustTable;
    public ToggleGroup FilterToggle;

    /**

     Initializes the main screen by setting the data and columns for each table view and updating the data set.
     lambdas were used in this method for initializing the cellValueFactories.
     the setCellValueFactory method takes an instance of the callback interface which is a functional interface
     and can thereby be implemented with an arrow function lambda expression.
     */
    @FXML
    private void initialize() {
        DataSet.update();
        AppTable.setItems(DataSet.getAppointments());

        AppIdColumn.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
        AppTitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        AppTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        AppLocationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        AppDescColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        AppDateColumn.setCellValueFactory(cellData -> cellData.getValue().startProperty());
        AppDateColumn1.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        AppStartColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        AppEndColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
        AppContactColumn.setCellValueFactory(cellData -> cellData.getValue().contactIDProperty().asObject());
        AppCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty().asObject());
        AppUserColumn.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());
        CustTable.setItems(DataSet.getCustomers());

        CustIdColumn.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
        CustNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        CustAddressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        CustPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        CustStateProvinceColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivision().getName()));
        CustPostalColumn.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty());
    }
    /**

     Changes the scene based on the provided FXML file.
     @param fxml the FXML file to load the new scene
     @param stage the stage on which to display the new scene
     @param selected an Optional of the selected item from the table view
     @throws IOException if an error occurs during the scene change
     */
    public void changeScene(String fxml, Stage stage, Optional<Object> selected) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/wgusoftware2scheduler/resources/" + fxml));
        if (selected.isPresent()) {

            Parent root = fxmlLoader.load();

            CanPass controller = fxmlLoader.getController();

            controller.passData(selected.get());

            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.show();
        } else {

            Parent root = fxmlLoader.load();

            fxmlLoader.getController();

            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.show();
        }
    }
    /**

     Handles the logout button action by changing the scene to the login view.
     @throws IOException if an error occurs during the scene change
     */
    @FXML
    public void onLogoutButtonClicked() throws IOException {

        Stage stage = (Stage) LogoutButton.getScene().getWindow();

        try {
            changeScene("Login.fxml", stage, Optional.ofNullable(null));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**

     Handles the reports button action by changing the scene to the reports view.
     */
    @FXML
    public void onReportsButtonClicked() {

        Stage stage = (Stage) ReportsButton.getScene().getWindow();

        try {
            changeScene("Reports.fxml", stage, Optional.ofNullable(null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**

     Handles the add button action for appointments by changing the scene to the appointment view.
     */
    @FXML
    public void onAppointmentsAddButtonClicked() {

        Stage stage = (Stage) LogoutButton.getScene().getWindow();

        try {
            changeScene("Appointment.fxml", stage, Optional.ofNullable(null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**

     Handles the update button action for appointments by changing the scene to the appointment view with the selected appointment.
     */
    @FXML
    public void onAppointmentsUpdateButtonClicked() {

        if (AppTable.getSelectionModel().getSelectedItem() == null) {
            Popups.error("Error", "Please select an appointment to update");
            return;
        }

        Stage stage = (Stage) LogoutButton.getScene().getWindow();

        try {
            changeScene("Appointment.fxml", stage, Optional.ofNullable(AppTable.getSelectionModel().getSelectedItem()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**

     Handles the delete button action for appointments by deleting the selected appointment from the database and refreshing the table view.
     */
    @FXML
    public void onAppointmentsDeleteButtonClicked() {

        if (AppTable.getSelectionModel().getSelectedItem() == null) {
            Popups.error("Error", "Please select an appointment to delete");
            return;
        }

        Appointment selected = (Appointment) AppTable.getSelectionModel().getSelectedItem();

        if (Popups.confirmDialog("Delete Appointment", "Are you sure you want to delete this appointment?")) {
            JDBC.deleteAppointment(selected.getID());
            DataSet.update();
            handleRadio();
        }
    }
    /**

     Handles the add button action for customers by changing the scene to the customer view.
     */
    @FXML
    public void onCustomerAddButtonClicked() {

        Stage stage = (Stage) LogoutButton.getScene().getWindow();

        try {
            changeScene("Customer.fxml", stage, Optional.ofNullable(null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**

     Handles the update button action for customers by changing the scene to the customer view with the selected customer.
     */
    @FXML
    public void onCustomerUpdateButtonClicked() {

        if (CustTable.getSelectionModel().getSelectedItem() == null) {
            Popups.error("Error", "Please select a customer to update");
            return;
        }

        Stage stage = (Stage) LogoutButton.getScene().getWindow();

        try {
            changeScene("Customer.fxml", stage, Optional.ofNullable(CustTable.getSelectionModel().getSelectedItem()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**

     Handles the delete button action for customers by deleting the selected customer from the database, along with all their associated appointments, and refreshing the table view.
     */
    @FXML
    public void onCustomerDeleteButtonClicked() {

        if (CustTable.getSelectionModel().getSelectedItem() == null) {
            Popups.error("Error", "Please select a customer to delete");
            return;
        }

        Customer selected = (Customer) CustTable.getSelectionModel().getSelectedItem();

        if (Popups.confirmDialog("Delete Customer",
                "Are you sure you want to delete this customer? This will delete all associated appointments.")) {
            DataSet.getAppointments().stream().filter(a -> a.getCustomerID() == selected.getID()).collect(Collectors.toList()).forEach(a -> JDBC.deleteAppointment(a.getID()));
            JDBC.deleteCustomer(selected.getID());
            DataSet.update();

            CustTable.setItems(DataSet.getCustomers());
            Popups.alert("Customer Deleted", selected.toString());
        }
    }
    /**

     Handles the radio button selection change by filtering the appointments table view based on the selected filter (week, month, or all appointments).
     */
    @FXML
    private void handleRadio(){
        RadioButton rb = (RadioButton) FilterToggle.getSelectedToggle();
        switch (rb.getText()){
            case "Current Week":
                AppTable.setItems(DataSet.sameWeekApptFilter());
                break;
            case "Current Month":
                AppTable.setItems(DataSet.sameMonthApptFilter());
                break;
            case "All Appointments":
                AppTable.setItems(DataSet.getAppointments());
                break;
        }
    }
}
