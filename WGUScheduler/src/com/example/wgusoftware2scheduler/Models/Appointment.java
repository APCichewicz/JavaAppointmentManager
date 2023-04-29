package com.example.wgusoftware2scheduler.Models;

import com.example.wgusoftware2scheduler.App;
import com.example.wgusoftware2scheduler.Utility.TimeHandler;
import javafx.beans.property.*;

import java.time.*;
import java.util.stream.Stream;
/**
 * The Appointment class represents an appointment with various attributes and provides methods
 * for validating and checking time overlaps. This class uses JavaFX properties to enable data
 * binding with the user interface.
 */
public class Appointment {
    private IntegerProperty ID;
    private StringProperty title;
    private StringProperty description;
    private StringProperty location;
    private StringProperty type;
    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> end;
    private IntegerProperty ContactID;
    private IntegerProperty CustomerID;
    private IntegerProperty UserID;

    public Appointment(int ID, String title, String description, String location, String type, LocalDateTime Start,
                       LocalDateTime End, int CustomerID, int UserID, int ContactID) {
        this.ID = new SimpleIntegerProperty(ID);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.type = new SimpleStringProperty(type);
        this.start = new SimpleObjectProperty<LocalDateTime>(Start.atZone(ZoneId.systemDefault()).toLocalDateTime());
        this.end = new SimpleObjectProperty<LocalDateTime>(End.atZone(ZoneId.systemDefault()).toLocalDateTime());
        this.ContactID = new SimpleIntegerProperty(ContactID);
        this.CustomerID = new SimpleIntegerProperty(CustomerID);
        this.UserID = new SimpleIntegerProperty(UserID);
    }

    public Appointment() {
        this.ID = new SimpleIntegerProperty(0);
        this.title = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.location = new SimpleStringProperty("");
        this.type = new SimpleStringProperty("");
        this.start = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
        this.end = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
        this.ContactID = new SimpleIntegerProperty(0);
        this.CustomerID = new SimpleIntegerProperty(0);
        this.UserID = new SimpleIntegerProperty(0);
    }

    // setters
    public void setID(int ID) {
        this.ID.set(ID);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setStart(LocalDateTime start) {
        this.start.set(start);
    }

    public void setEnd(LocalDateTime end) {
        this.end.set(end);
    }

    public void setStartTime(LocalTime startTime) {
        this.start.set(LocalDateTime.of(start.get().toLocalDate(), startTime));
    }

    public void setEndTime(LocalTime endTime) {
        this.end.set(LocalDateTime.of(end.get().toLocalDate(), endTime));
    }

    public void setContactID(int ContactID) {
        this.ContactID.set(ContactID);
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID.set(CustomerID);
    }

    public void setUserID(int UserID) {
        this.UserID.set(UserID);
    }

    // getters
    public int getID() {
        return ID.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getType() {
        return type.get();
    }

    public LocalDate getStart() {
        return start.get().toLocalDate();
    }

    public LocalDate getEnd() {
        return end.get().toLocalDate();
    }

    public LocalTime getStartTime() {
        return start.get().toLocalTime();
    }

    public LocalDateTime getStartDateTime() {
        return start.get();
    }

    public LocalTime getEndTime() {
        return end.get().toLocalTime();
    }

    public LocalDateTime getEndDateTime() {
        return end.get();
    }

    public int getContactID() {
        return ContactID.get();
    }

    public int getCustomerID() {
        return CustomerID.get();
    }

    public int getUserID() {
        return UserID.get();
    }

    // properties
    public IntegerProperty IDProperty() {
        return ID;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty locationProperty() {
        return location;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public ObjectProperty<LocalDate> startProperty() {
        return new SimpleObjectProperty<LocalDate>(start.get().toLocalDate());
    }

    public ObjectProperty<LocalDate> endProperty() {
        return new SimpleObjectProperty<LocalDate>(end.get().toLocalDate());
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        return new SimpleObjectProperty<LocalTime>(start.get().toLocalTime());
    }

    public ObjectProperty<LocalTime> endTimeProperty() {
        return new SimpleObjectProperty<LocalTime>(end.get().toLocalTime());
    }

    public IntegerProperty contactIDProperty() {
        return ContactID;
    }

    public IntegerProperty customerIDProperty() {
        return CustomerID;
    }

    public IntegerProperty userIDProperty() {
        return UserID;
    }
    /**
     * Sets all attributes of the current appointment object with the attributes of the given appointment object.
     *
     * @param a the appointment object with the attributes to be copied.
     */
    public void setAll(Appointment a) {
        this.ID.set(a.getID());
        this.title.set(a.getTitle());
        this.description.set(a.getDescription());
        this.location.set(a.getLocation());
        this.type.set(a.getType());
        this.setStart(a.getStart().atStartOfDay());
        this.setStartTime(a.getStartTime());
        this.setEnd(a.getEnd().atStartOfDay());
        this.setEndTime(a.getEndTime());
    }
    /**
     * Returns a string representation of the Appointment object.
     *
     * @return a string representation of the Appointment object
     */
    @Override
    public String toString() {
        return "Appointment{" + "ID=" + ID.get() + ", title=" + title.get() + ", description=" + description.get()
                + ", location=" + location.get() + ", type=" + type.get() + ", start=" + start.get() + ", end="
                + end.get()
                + ", ContactID=" + ContactID.get() + ", CustomerID=" + CustomerID.get() + ", UserID=" + UserID.get();
    }
    /**
     * Validates the appointment data by checking if the provided fields are not empty, if the start date is before
     * the end date, if the start time is before the end time, and if the appointment is within the business hours
     * of 08:00 and 22:00 EST.
     *
     * @param title       the title of the appointment
     * @param description the description of the appointment
     * @param location    the location of the appointment
     * @param type        the type of the appointment
     * @param startDate   the start date and time of the appointment
     * @param endDate     the end date and time of the appointment
     * @param userID      the user ID associated with the appointment
     * @param customerID  the customer ID associated with the appointment
     * @param contactID   the contact ID associated with the appointment
     * @return true if the appointment data is valid, otherwise an IllegalArgumentException is thrown
     * @throws IllegalArgumentException if any of the validation checks fail
     */
    public static boolean isValid(String title, String description, String location, String type,
                                  LocalDateTime startDate, LocalDateTime endDate, int userID, int customerID, int contactID)
            throws IllegalArgumentException {
        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || startDate == null
                || endDate == null || userID == 0 || customerID == 0 || contactID == 0) {
            throw new IllegalArgumentException("All fields must be filled out");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (startDate.toLocalTime().isAfter(endDate.toLocalTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if (!(TimeHandler.isWithinBusinessHours(startDate)) || !(TimeHandler.isWithinBusinessHours(endDate))) {
            throw new IllegalArgumentException("Appointment must be within business hours of 08:00 and 22:00 EST");


        }
        return true;
    }
    /**
     * Check if the appointment time overlaps with any existing appointments for the user.
     *
     * @param startDate the start date/time of the appointment
     * @param endDate the end date/time of the appointment
     * @param userID the user ID associated with the appointment
     *
     * @throws IllegalArgumentException if the appointment overlaps with another appointment
     *
     * @return true if the appointment time does not overlap with any existing appointments for the user
     */
    public static boolean checkTimeOverlap(LocalDateTime startDate, LocalDateTime endDate, int userID) {
        DataSet.getAppointments().stream()
                .filter(a -> a.getUserID() == userID)
                .forEach((a) -> {
                    if ((a.getStartDateTime().equals(startDate) || a.getStartDateTime().isAfter(startDate)) &&
                            (a.getStartDateTime().isBefore(endDate) || a.getStartDateTime().equals(endDate))) {
                        throw new IllegalArgumentException("Appointment overlaps with another appointment");
                    }
                    if ((a.getEndDateTime().equals(startDate) || a.getEndDateTime().isAfter(startDate)) &&
                            (a.getEndDateTime().isBefore(endDate) || a.getEndDateTime().equals(endDate))) {
                        throw new IllegalArgumentException("Appointment overlaps with another appointment");
                    }
                });
        return true;
    }
    /**
     * Checks if the given time interval overlaps with any of the existing appointments
     * for the given user.
     *
     * Makes use of Lambda expressions in coordination with the java streams for simpler processing. for example the filter
     * function takes a predecate and returns ture or false and is implemented via the arrow function form of a lambda expression
     *
     * @param startDate the start date and time of the appointment
     * @param endDate the end date and time of the appointment
     * @param userID the ID of the user for whom to check the appointments
     * @param appID the ID of the appointment being checked (excluded from the check)
     * @return true if the time interval does not overlap with any existing appointments,
     *         false otherwise
     * @throws IllegalArgumentException if the time interval overlaps with an existing
     *         appointment for the given user
     */
    public static boolean checkTimeOverlap(LocalDateTime startDate, LocalDateTime endDate, int userID, int appID) {
        DataSet.getAppointments().stream()
                .filter(a -> a.getID() != appID)
                .filter(a -> a.getUserID() == userID)
                .forEach((a) -> {
                    if ((a.getStartDateTime().equals(startDate) || a.getStartDateTime().isAfter(startDate)) &&
                            (a.getStartDateTime().isBefore(endDate) || a.getStartDateTime().equals(endDate))) {
                        throw new IllegalArgumentException("Appointment overlaps with another appointment");
                    }
                    if ((a.getEndDateTime().equals(startDate) || a.getEndDateTime().isAfter(startDate)) &&
                            (a.getEndDateTime().isBefore(endDate) || a.getEndDateTime().equals(endDate))) {
                        throw new IllegalArgumentException("Appointment overlaps with another appointment");
                    }
                });
        return true;
    }
}

