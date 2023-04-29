package com.example.wgusoftware2scheduler.Controllers;

import com.example.wgusoftware2scheduler.Models.Appointment;
import com.example.wgusoftware2scheduler.Models.ByMonthTypeReport;
import com.example.wgusoftware2scheduler.Models.HourlyVolumeReport;
import com.example.wgusoftware2scheduler.Models.DataSet;
import com.example.wgusoftware2scheduler.Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;
/**

 ReportController is a class that handles the functionality for the reports screen of the application.

 It displays reports related to appointments, such as appointments by month and type, hourly volume, and appointments by contact.
 */
public class ReportController {

    public TableColumn<Appointment, Integer> ApptIdColumn;
    public TableColumn<Appointment, String> ApptTypeColumn;
    public TableColumn<Appointment, String> ApptTitleColumn;
    public TableColumn<Appointment, String> ApptDescColumn;
    public TableColumn<Appointment, String> ApptLocationColumn;
    public TableColumn<Appointment, LocalDate> ApptStartDateColumn;
    public TableColumn<Appointment, LocalDate> ApptEndDateColumn;
    public TableColumn<Appointment, LocalTime> ApptStartTimeColumn;
    public TableColumn<Appointment, LocalTime> ApptEndTimeColumn;
    public TableColumn<Appointment, Integer> ApptCustomerIdColumn;
    public TableColumn<ByMonthTypeReport, String> ApptMonthColumn;
    public TableColumn<ByMonthTypeReport, String> ApptTypeAggregateColumn;
    public TableColumn<ByMonthTypeReport, Integer> TotalApptColumn;
    public TableColumn<HourlyVolumeReport, String> HourColumn;
    public TableColumn<HourlyVolumeReport, Integer> NumberofApptsColumn;
    public TableColumn<HourlyVolumeReport, Double> AverageApptLengthColumn;
    public TableView ApptByMonthTableView;
    public TableView hourlyVolumeTable;
    public TableView AppointmentTableView;
    public ComboBox ContactsComboBox;
    public Button BackButton;
    private ObservableList<Appointment> temp = FXCollections.observableArrayList();
    /**

     Initializes the reports screen by setting the data and columns for each table view.
     */
    public void initialize() {
        ApptByMonthTableView.setItems(JDBC.appointmentByMonthTypeReport());
        ApptMonthColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        ApptTypeAggregateColumn.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        TotalApptColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        hourlyVolumeTable.setItems(JDBC.hourlyVolumeReport());
        HourColumn.setCellValueFactory(cellData -> cellData.getValue().hourProperty());
        NumberofApptsColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        AverageApptLengthColumn.setCellValueFactory(cellData -> cellData.getValue().averageProperty().asObject());

        AppointmentTableView.setItems(DataSet.getAppointments());
        ApptIdColumn.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
        ApptTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        ApptTitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        ApptDescColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        ApptLocationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        ApptStartDateColumn.setCellValueFactory(cellData -> cellData.getValue().startProperty());
        ApptEndDateColumn.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        ApptStartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        ApptEndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
        ApptCustomerIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty().asObject());

        DataSet.getContacts().stream().forEach(contact -> {
            ContactsComboBox.getItems().add(contact.getName());
        });
    }
    /**

     Handles the back button action by changing the scene to the main view.
     @param event the ActionEvent triggered by the back button
     */
    @FXML
    private void onBackButtonClicked(ActionEvent event) {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(
                    ReportController.class.getResource("/com/example/wgusoftware2scheduler/resources/MainView.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**

     Handles the selection change in the contacts combo box by filtering the appointment table view based on the selected contact.
     */
    @FXML
    private void onComboBoxSelectionChange() {
        temp.clear();

        String selectedContact = ContactsComboBox.getSelectionModel().getSelectedItem().toString();

        int contactId = DataSet.getContacts().stream().filter(contact -> contact.getName().equals(selectedContact))
                .findFirst().get().getID();

        temp.addAll(DataSet.getAppointments().stream().filter(a -> a.getContactID() == contactId)
                .collect(Collectors.toList()));
        AppointmentTableView.setItems(temp);
        AppointmentTableView.refresh();
    }

}
