package com.example.wgusoftware2scheduler.Models;

import com.example.wgusoftware2scheduler.Utility.TimeHandler;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import com.example.wgusoftware2scheduler.Utility.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
/**
 * This class is used to store and manage the data for the software application.
 * It has a singleton design pattern and contains the customer, appointment, country,
 * contact, user and division data in separate ObservableLists.
 */
public class DataSet {
    private static DataSet instance = null;

    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private static ObservableList<Country> countries = FXCollections.observableArrayList();
    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static ObservableList<Division> divisions = FXCollections.observableArrayList();
    /**
     * Private constructor for the DataSet.
     * It initializes the customers, appointments, countries, contacts, users,
     * and divisions data.
     */
    private DataSet() {
        customers = JDBC.getCustomers();
        appointments = JDBC.getAppointments();
        countries =  JDBC.getCountries();
        contacts = JDBC.getContacts();
        users = JDBC.getUsers();
        divisions = JDBC.getDivisions();
    }
    /**
     * Returns the instance of the DataSet.
     * If the instance is null, a new instance is created.
     *
     * @return the instance of the DataSet
     */
    public static synchronized DataSet getInstance() {
        if (instance == null) {
            instance = new DataSet();
        }
        return instance;
    }
    /**
     * Updates the appointments and customers data.
     */
    public static void update(){
        appointments = JDBC.getAppointments();
        customers = JDBC.getCustomers();
    }
    /**
     * Returns the customer with the specified customer object.
     *
     * @param c the customer to find
     * @return the customer with the specified customer object
     */
    public static synchronized Customer getCustomer(Customer c) {
        for (Customer customer : customers) {
            if (customer.getID() == c.getID()) {
                return customer;
            }
        }
        return null;
    }
    /**
     * Returns the customer with the specified customer ID.
     *
     * @param i the customer ID to find
     * @return the customer with the specified customer ID
     */
    public static synchronized Customer getCustomer(int i) {
        return customers.stream().filter(c -> c.getID() == i).findFirst().get();
    }
    /**
     * Returns the customer with the specified name.
     *
     * @param name the name of the customer to find
     * @return the customer with the specified name
     */
    public static synchronized Customer getCustomer(String name) {
        return customers.stream().filter(c -> c.getName().equals(name)).findFirst().get();
    }
    /**
     * Returns the contact with the specified contact ID.
     *
     * @param i the contact ID to find
     * @return the contact with the specified contact ID
     */
    public static synchronized Contact getContact(int i) {
        return contacts.stream().filter(c -> c.getID() == i).findFirst().get();
    }
    /**
     * Returns the contact with the specified name.
     *
     * @param name the name of the contact to find
     * @return the contact with the specified name
     */
    public static synchronized Contact getContact(String name) {
        return contacts.stream().filter(c -> c.getName().equals(name)).findFirst().get();
    }
    /**
     * Returns the user with the specified user ID.
     *
     * @param i the user ID to find
     * @return the user with the specified user ID
     */
    public static synchronized User getUser(int i) {
        return users.stream().filter(u -> u.getID() == i).findFirst().get();
    }
    /**
     * Returns the user with the specified name.
     *
     * @param name the name of the user to find
     * @return the user with the specified name
     */
    public static synchronized User getUser(String name) {
        return users.stream().filter(u -> u.getName().equals(name)).findFirst().get();
    }
    /**
     * Returns the appointment with the specified appointment ID.
     *
     * @param i the appointment ID to find
     * @return the appointment with the specified appointment ID
     */
    public static synchronized Appointment getAppointment(int i) {
        return appointments.stream().filter(a -> a.getID() == i).findFirst().get();
    }
    /**
     * Returns the list of customers.
     *
     * @return the list of customers
     */
    public static synchronized ObservableList<Customer> getCustomers() {
        return customers;
    }
    /**
     * Returns the list of appointments.
     *
     * @return the list of appointments
     */
    public static synchronized ObservableList<Appointment> getAppointments() {
        return appointments;
    }
    /**
     * Returns the list of countries.
     *
     * @return the list of countries
     */
    public static synchronized ObservableList<Country> getCountries() {
        return countries;
    }
    /**
     * Returns the list of contacts.
     *
     * @return the list of contacts
     */
    public static synchronized ObservableList<Contact> getContacts() {
        return contacts;
    }
    /**
     * Get the list of users.
     *
     * @return the list of users
     */
    public static synchronized ObservableList<User> getUsers() {
        return users;
    }
    /**
     * Get the list of divisions.
     *
     * @return the list of divisions
     */
    public static ObservableList<Division> getDivisions() {
        return divisions;
    }
    /**
     * Set the list of customers.
     *
     * @param customers the list of customers
     */
    public static synchronized void setCustomers(ObservableList<Customer> customers) {
        DataSet.customers = customers;
    }
    /**
     * Set the list of appointments.
     *
     * @param appointments the list of appointments
     */
    public static synchronized void setAppointments(ObservableList<Appointment> appointments) {
        DataSet.appointments = appointments;
    }
    /**
     * Set a customer in the list of customers.
     *
     * @param c the customer to be set in the list
     */
    public static synchronized void setCustomer(Customer c) {
        for (Customer customer : customers) {
            if (customer.getID() == c.getID()) {
                customer = c;
            }
        }
    }
    /**
     * Set an appointment in the list of appointments.
     *
     * @param a the appointment to be set in the list
     */
    public static synchronized void setAppointment(Appointment a) {
        for (Appointment appointment : appointments) {
            if (appointment.getID() == a.getID()) {
                appointment = a;
            }
        }
    }
    /**
     * Add a customer to the list of customers.
     *
     * @param c the customer to be added to the list
     */
    public static synchronized void addCustomer(Customer c) {
        customers.add(c);
    }
    /**
     * Add an appointment to the list of appointments.
     *
     * @param a the appointment to be added to the list
     */
    public static synchronized void addAppointment(Appointment a) {
        appointments.add(a);
    }
    /**
     * Remove a customer from the list of customers.
     *
     * @param c the customer to be removed from the list
     */
    public static synchronized void removeCustomer(Customer c) {
        customers.remove(c);
    }
    /**
     * Remove an appointment from the list of appointments.
     *
     * @param a the appointment to be removed from the list
     */
    public static synchronized void removeAppointment(Appointment a) {
        appointments.remove(a);
    }
    /**
     * Get the list of appointments for the current week.
     *
     * @return the list of appointments for the current week
     */
    public static synchronized ObservableList<Appointment> sameWeekApptFilter() {
        ObservableList<Appointment> sameWeekAppt = FXCollections.observableArrayList();
        appointments.stream().filter(a -> TimeHandler.sameWeek(a.getStart(), TimeHandler.getTodayUTC()))
                .forEach(a -> sameWeekAppt.add(a));
        return sameWeekAppt;
    }
    /**

     Filters the appointments in the current month.
     @return a list of appointments in the current month
     */
    public static synchronized ObservableList<Appointment> sameMonthApptFilter() {
        ObservableList<Appointment> sameMonthAppt = FXCollections.observableArrayList();
        appointments.stream().filter(a -> TimeHandler.sameMonth(a.getStart(), TimeHandler.getTodayUTC()))
                .forEach(a -> sameMonthAppt.add(a));
        return sameMonthAppt;
    }

}
